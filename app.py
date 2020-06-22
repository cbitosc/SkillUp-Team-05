from flask import Flask,jsonify
from flask_restful import Api
from flask_jwt_extended import JWTManager
from resources.place import Phase,PlaceLogin,Checkusers,Registerstud,Userregistrstiondet,AdminAnnounce,Addcompany,UserAnnouncecheck,DateStatsforplace,Datestatsforcom,Datestatsforann


app=Flask(__name__)
app.config['PROPAGATE_EXCEPTIONS']=True
app.config['JWT_SECRET_KEY']='coscskillup'
api=Api(app)
jwt=JWTManager(app)


@jwt.unauthorized_loader
def missing_token_callback(error):
    return jsonify({'error':'authourisation required','description':'request doesnot contain an access token'}),401


@jwt.invalid_token_loader
def invalid_token_callback(error):
    return jsonify({'error':'invalid token','description':'signature verification failed'}),401

api.add_resource(Phase,'/placesignup')
api.add_resource(PlaceLogin,'/login')
api.add_resource(Registerstud,'/regstud')
api.add_resource(UserAnnouncecheck,'/anntostud')
api.add_resource(Checkusers,'/userscheckadmin')
api.add_resource(Userregistrstiondet,'/userregcomadmin')
api.add_resource(AdminAnnounce,'/announceadmin')
api.add_resource(Addcompany,'/addcomadmin')
api.add_resource(DateStatsforplace,'/plastudate')
api.add_resource(Datestatsforcom,'/comstudate')
api.add_resource(Datestatsforann,'/anndate')
if __name__ == "__main__":
    app.run()
