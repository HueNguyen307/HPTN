from django.db import models


class Genre(models.Model):
    id = models.AutoField(primary_key=True)
    name=models.CharField(max_length=255)
    description=models.CharField(max_length=255)
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()

class Title(models.Model):
    id = models.AutoField(primary_key=True)
    genreId =models.ForeignKey(Genre, on_delete = models.CASCADE)
    name=models.CharField(max_length=255)
    author=models.CharField(max_length=255)
    description=models.CharField(max_length=255)
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()

class Chapter(models.Model):
    titleId=models.ForeignKey(Title, on_delete = models.CASCADE)
    name=models.CharField(max_length=255)
    number=models.IntegerField()
    # views=models.IntegerField()
    content=models.TextField()
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()



# class Viewed(models.Model):
#     userid = models.BigIntegerField(default=0)
#     chapterId=models.ForeignKey(Chapter, on_delete = models.CASCADE)
#     view_at=models.DateTimeField()
#     views = models.IntegerField(default=0)

