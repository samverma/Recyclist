import requests
mobile_token = 'we4mluxnRqNfCTZe2ccWEmWpa28OQCwv'
token_data = {'mobile_token':mobile_token}
login_data = {
'email': 'd@mail.com',
'password': 'password'
}
querydata = {
'query':'',
'state':'',
'cost':'All',
'tag':''
}
domain = 'http://recyclist.us/'
querydata.update(token_data)
#r = requests.post(domain + 'api/listing/search',querydata)
#token = r.json()['token']
#print(r.text)
#header = {'Authorization': 'Bearer ' + token}
searchid={
'search_id':'27',
'page':'1',
'count':'5'
}
r = requests.get(domain + 'api/listing/search?mobile_token=we4mluxnRqNfCTZe2ccWEmWpa28OQCwv', params=searchid)
print(r.text)