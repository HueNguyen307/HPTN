from django.contrib import admin
from .models import Genre,Chapter,Title
# Register your models here.
admin.site.register([Genre,Chapter,Title])
