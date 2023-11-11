from django.urls import path
from . import views

urlpatterns = [
    path("title/",views.get_Title),
    path("add_title/", views.add_Title),
    path("genre/title/", views.filter_title),
    path("update_title/", views.update_title),
    path('search_title_by_name/', views.search_title_by_name_or_author),
    path("delete_title/",views.delete_title_byID)
]
