from calendar import c
from unicodedata import name
from urllib import response
from xmlrpc.client import DateTime
from django.http import HttpResponse
from content_model.models import Chapter, Title
import json
import datetime
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from django.db.models import Q
import requests

@api_view(['POST'])
def get_chapter_info(request):
    if 'application/json' in request.META.get('CONTENT_TYPE',''):
        request_data = json.loads(request.body)
        
        chapterid = request_data.get('chapterid')

        if chapterid==None:
            return Response({'error':'Hãy điền đầy đủ các trường'},status=status.HTTP_400_BAD_REQUEST)
        else:
            chapterid=int(chapterid)

        try:
            chapter_=Chapter.objects.filter(id=chapterid)[0]
            #tao response data
            response_data={}
            response_data['id']=chapterid
            response_data['titleId']=chapter_.titleId_id
            response_data['name']=chapter_.name
            response_data['number']=chapter_.number
            response_data['content']=chapter_.content
            response_data['created_at']=chapter_.created_at
            response_data['updated_at']=chapter_.updated_at
            
            return Response(response_data,status=status.HTTP_200_OK)
        except Exception as e:
            print(e)
            return Response({'error':'Đã có lỗi xảy ra'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    else:
        return Response({'error':'Lỗi định dạng request'},status=status.HTTP_400_BAD_REQUEST)


@csrf_exempt
def add_chapter(request):
    resp={}
    if request.method == 'POST':    
        request_data = json.loads(request.body)

        number = request_data.get('number')
        name = request_data.get('name')
        titleid = request_data.get('titleid')
        content = request_data.get('content')

        if number!=None and name!=None and titleid!=None and content!=None  :  
           
                if Title.objects.filter(id=titleid).count()>0:         
                   
                        try:
                            created_at=datetime.datetime.now().date()
                            updated_at=datetime.datetime.now().date()
                            chapter_data=Chapter(
                                titleId_id=titleid,
                                name=name,
                                number=number,
                                content=content,
                                created_at=created_at,
                                updated_at=updated_at
                            )
                            chapter_data.save()

                            data=Chapter.objects.filter(
                                titleId_id=titleid,
                                name=name,
                                number=number,
                                content=content,
                                created_at=created_at,
                                updated_at=updated_at
                            ).values()[0]

                            resp['status'] = 'Success'
                            resp['status_code'] = '200'
                            resp['data'] = data
                        except Exception as e:
                            print(e)
                            resp['status'] = 'Failed'
                            resp['status_code'] = '500'
                            resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                    
                else:
                    resp['status'] = 'Failed'
                    resp['status_code'] = '400'
                    resp['error'] = 'Title id không tồn tại.'
        else:
            resp['status'] = 'Failed'
            resp['status_code'] = '400'
            resp['error'] = 'Hãy điền đầy đủ các trường.'
    else:
        resp['status'] = 'Failed'
        resp['status_code'] = '400'
        resp['error'] =  'Request method is not POST'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   
   
@csrf_exempt
def update_chapter(request):
    resp={}
    if request.method == 'PUT':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)

            chapterid = request_data.get('chapterid')
            number = request_data.get('number')
            name = request_data.get('name')
            titleid = request_data.get('titleid')
            content = request_data.get('content')

            if chapterid!=None and number!=None and name!=None and titleid!=None and content!=None :  
                    if Chapter.objects.filter(id=chapterid).count()>0:
                        if Title.objects.filter(id=titleid).count()>0:         
                                try:
                                    chapter_data=Chapter.objects.filter(id=chapterid)[0]
                                    chapter_data.titleid=titleid
                                    chapter_data.name=name
                                    chapter_data.number=number
                                    chapter_data.content=content
                                    chapter_data.updated_at=datetime.datetime.now().date()

                                    chapter_data.save()

                                    data=Chapter.objects.filter(id=chapterid).values()[0]

                                    resp['status'] = 'Success'
                                    resp['status_code'] = '200'
                                    resp['data'] = data
                                except Exception as e:
                                    print(e)
                                    resp['status'] = 'Failed'
                                    resp['status_code'] = '500'
                                    resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                        else:
                            resp['status'] = 'Failed'
                            resp['status_code'] = '400'
                            resp['error'] = 'Title id không tồn tại.'
                    else:
                        resp['status'] = 'Failed'
                        resp['status_code'] = '400'
                        resp['error'] = 'Chapter id không tồn tại.'
            else:
                resp['status'] = 'Failed'
                resp['status_code'] = '400'
                resp['error'] = 'Hãy điền đầy đủ các trường.'
        else:
            resp['status'] = 'Failed'
            resp['status_code'] = '400'
            resp['error'] =  'Request type is not matched.'
    else:
        resp['status'] = 'Failed'
        resp['status_code'] = '400'
        resp['error'] =  'Request method is not PUT'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   




@api_view(['GET'])
def chapterList(request):
    titleID = request.GET.get('titleID')
    if not titleID:
        return Response({'error' : "Vui lòng điền đầy đủ các trường"}, status=status.HTTP_400_BAD_REQUEST)
    title = Title.objects.filter(id = titleID)
    if title.exists():
        title = title.values()[0]
        list = []
        for i in Chapter.objects.filter(titleId = title.get('id')).order_by("number"):
            chapter = {}
            chapter['id'] = i.id 
            chapter['name'] = i.name
            chapter['number'] = i.number
            chapter['content'] = i.content
            list.append(chapter)
        return(Response(list))
    else :
        return Response({'error' : "Title id không tồn tại"}, status=status.HTTP_400_BAD_REQUEST)
    
