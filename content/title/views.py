from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import Title,Genre
from .serializer import TitleSerializer, GenreSerializer, UpdateTitleSerializer
from datetime import date, datetime
import json
import requests
from django.http import JsonResponse
from rest_framework.decorators import api_view
from django.db.models import Q
# Create your views here.

@api_view(['GET'])
# lấy thông tin truyện
def get_Title(request):
    # try:
        titleid = request.query_params.get('titleid')
        titles=Title.objects.get(id=titleid)
        serializer=TitleSerializer(titles, many=False)
        res = serializer.data

        return Response(res)
    # except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)




@api_view(['DELETE'])
def delete_title_byID(request):
    titleid=request.query_params.get("titleid")
    try:
        obj=Title.objects.get(id=titleid)
        try:
            obj.delete()
            return Response({"message":"Xóa truyện thành công."}, status=status.HTTP_200_OK)
        except:
            return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    except:
        return Response({"error":"Loại truyện không tồn tại."},status=status.HTTP_400_BAD_REQUEST)

# ????
@api_view(['GET'])
# lấy danh sách truyện thuộc thể loại
def filter_title(request):
    # try:
        genreID=request.query_params.get('genreID')
        # page=request.query_params.get('page')
        
        if not (genreID):
            return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        try:
            Genre.objects.get(id=genreID)
        except:
            return Response({"error":"Genre id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        
        tmp=Title.objects.filter(genreId=genreID)
        list=[]
        for title in tmp:
            list.append({"id":title.id,
                        "name":title.name,
                        "author":title.author,
                        "description":title.description,
                        })
            
        return Response(list, status=status.HTTP_200_OK) 
 

@api_view(['POST'])
def add_Title(request):
    # thêm đầu truyện
    
    name=request.data.get('name')
    author=request.data.get('author')
    genreid=request.data.get('genreid')
    description=request.data.get('description')
   
    if not (name and  description and author and genreid ):
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    try:
        Genre.objects.get(id=genreid)
    except:
        return Response({"error":"Genre id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    
    data={        
        'name':name,
        'author':author, 
        'genreId' : genreid,
        'description':description,
        'created_at': datetime.now(),
        'updated_at': datetime.now(),
    }
    try:
        Title.objects.get( name=name)
        return Response({"error":"Sách đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    except:
        serializer = TitleSerializer(data=data)
        # print(serializer)
        if serializer.is_valid():
            serializer.save()  

            return Response({"message":" Thêm  sach thành công."}, status=status.HTTP_200_OK)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    # if request.method == 'POST':
    #     name=request.data.get('name')
    #     author=request.data.get('author')
    #     genreid=request.data.get('genreid')
    #     description=request.data.get('description')

    #     try:
    #         genre = Genre.objects.get(id=genreid)
    #     except Genre.DoesNotExist:
    #         return JsonResponse({'error': 'Genre does not exist'}, status=400)

    #     title = Title.objects.create(name=name,author=author, genreid=genreid,description=description )

    #     return JsonResponse({'success': 'Book added successfully'}, status=201)

    # return JsonResponse({'error': 'Invalid request method'}, status=400)

@api_view(['PUT'])
def update_title(request):
    # sửa đầu truyện

    name=request.data.get('name')
    author=request.data.get('author')
    description=request.data.get('description')
    genreid=request.data.get('genreid')
    titleid=request.data.get("titleid")
  
    if not ( titleid and name  and description and author and genreid):
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
  
    try:
        tmp=Title.objects.get(id=titleid)
    except:
        return Response({"error":"Sách không tồn tại đẻe sửa"},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'name':name,
        'author':author,
        'description':description,
        'genreId':genreid,
        # 'created_at': Genre.objects.get("created_at"),
        'updated_at': datetime.now(),
    }
    # try:
    Title.objects.get(id=titleid)
    serializer = UpdateTitleSerializer(tmp, data=data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
        
    # except:
    #     return Response({"error":"Sách không tồn tại"},status=status.HTTP_400_BAD_REQUEST)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

#Tim kiem theo ten hoac theo tac gia
@api_view(['GET'])
def search_title_by_name_or_author(request):
    name_search=request.GET.get('name')
    author_search=request.GET.get('author')

    if name_search==None or author_search==None:
        return Response({'error':'hãy điền đầy đủ thông tin'},status=status.HTTP_400_BAD_REQUEST)

    # try:   
        #tim kiem
        #list_search_title=Title.objects.filter(name__contains=name_search,author__contains=author_search).values()
    list_search_title=Title.objects.filter(Q(name__contains=name_search)|Q(author__contains=author_search)).values()

    #tao response data
    response_data=[]
    for i in range(len(list_search_title)):
        object = {}
        object['id']=list_search_title[i]['id']
        object['name']=list_search_title[i]['name']
        object['author']=list_search_title[i]['author']
        object['description']=list_search_title[i]['description']
        response_data.append(object)

    return Response(response_data,status=status.HTTP_200_OK)
    # except Exception as e:
    #     print(e)
    #     return Response({'error':'Đã có lỗi xảy ra'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
