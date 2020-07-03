from django.shortcuts import render
from django.http import HttpResponse
import requests

# Create your views here.

def home(request):
	return render(request,'home/home.html')

def about(request):
	return render(request,'home/about.html')

def checkusers(request):
    data=requests.get("https://placementcosc.herokuapp.com/userscheckadmin")
    data=data.json()
    context={'data':data}
    return render(request,'home/check.html',context)

def addcompanyform(request):
	return render(request,'home/addcompanyform.html')

def addcompany(request):
	company_name=request.POST.get('company_name','')
	job_name=request.POST.get('job_name','')
	job_description=request.POST.get('job_description','')
	job_requirements=request.POST.get('job_requirements','')
	min_gpa=request.POST.get('min_gpa','')

	data=requests.post("https://placementcosc.herokuapp.com/addcomadmin",data={"company_name":company_name,
		"job_name":job_name,
		"job_description":job_description,
		"job_requirements":job_requirements,
		"min_gpa":min_gpa
		})
	data=data.json()

	return HttpResponse(data["message"])


def adminannounceform(request):
	return render(request,'home/adminannounceform.html')


def adminannounce(request):
	type=request.POST.get('type','')
	title_description=request.POST.get('title_description','')
	date_event=request.POST.get('date_event','')
	time_event=request.POST.get('time_event','')
	event_description=request.POST.get('event_description','')
	image_link=request.POST.get('image_link','')

	data=requests.post("https://placementcosc.herokuapp.com/announceadmin",data={"type":type,
		"title_description":title_description,
		"date_event":date_event,
		"time_event":time_event,
		"event_description":event_description,
		"image_link":image_link
		})
	data=data.json()

	return HttpResponse(data["message"])

def userregdetailsform(request):
	return render(request,'home/userregdetailsform.html')

def userregdetails(request):
	company_name=request.POST.get('company_name','')
	data=requests.get("https://placementcosc.herokuapp.com/userregcomadmin",data={"company_name":company_name})
	data=data.json()
	context1={'data':data}
	return render(request,'home/userregdetails.html',context1)

def datestatsforplace(request):
	Date=request.POST.get('Date','')
	data=requests.get("https://placementcosc.herokuapp.com/plastudate",data={"Date":Date})
	data=data.json()
	context1={'data':data}
	return render(request,'home/datestatsplace.html',context1)

def datestatsforplaceform(request):
	return render(request,'home/datestatsforplaceform.html')


def datestatsforcom(request):
	Date=request.POST.get('Date','')
	data=requests.get("https://placementcosc.herokuapp.com/comstudate",data={"Date":Date})
	data=data.json()
	context1={'data':data}
	return render(request,'home/datestatscom.html',context1)

def datestatsforcomform(request):
	return render(request,'home/datestatsforcomform.html')

def datestatsforann(request):
	Date=request.POST.get('Date','')
	data=requests.get("https://placementcosc.herokuapp.com/anndate",data={"Date":Date})
	data=data.json()
	context1={'data':data}
	return render(request,'home/datestatsann.html',context1)

def datestatsforannform(request):
	return render(request,'home/datestatsforannform.html')