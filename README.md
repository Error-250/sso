# sso
Use oauth2.0 + jwt implement authentication server

### usage
POST /oauth/token
client_id      required
client_secret  required
username       required
password       required
grant_type     required

GET   /oauth/check_token
token          required