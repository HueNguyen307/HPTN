from content_model.models import Genre
from rest_framework import serializers



class GenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        fields = '__all__'
    

class UpdateGenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        exclude=['created_at']

class AddGenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        fields = '__all__'
        # fields = ( 'name', 'description', 'updated_at')

        
