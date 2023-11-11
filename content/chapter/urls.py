from django.urls import path
from . import views

urlpatterns = [
    path('get_chapter_info/',views.get_chapter_info,name='get_chapter_info'),
    path('add_chapter/',views.add_chapter,name='add_chapter'),
    path('update_chapter/',views.update_chapter,name='update_chapter'),
    path('chapterList/', views.chapterList)
]