from . import views
from django.urls import path
urlpatterns = [
    path('',views.home, name='home'),
    path('about/',views.about,name='about'),
    path('check/',views.checkusers,name='check-user'),
    path('addcompanyform/',views.addcompanyform,name='addcompanyform'),
    path('addcompany/',views.addcompany,name='addcompany'),
    path('adminannounceform/',views.adminannounceform,name='adminannounceform'),
    path('adminannounce/',views.adminannounce,name='adminannounce'),
    path('userregcomadmin/',views.userregdetails,name='userregdetails'),
    path('userregcomadminform/',views.userregdetailsform,name='userregdetailsform'),
    path('datestatsforplace/',views.datestatsforplace,name='date-stats-place'),
    path('datestatsforplaceform/',views.datestatsforplaceform,name='date-stats-place-form'),
    path('datestatsforcom/',views.datestatsforcom,name='date-stats-com'),
    path('datestatsforcomform/',views.datestatsforcomform,name='date-stats-com-form'),
    path('datestatsforann/',views.datestatsforann,name='date-stats-ann'),
    path('datestatsforannform/',views.datestatsforannform,name='date-stats-ann-form')
]
