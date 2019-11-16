# sso
Use oauth2.0 + jwt implement authentication server

### usage
+ POST /oauth/token

    |字段名        | 是否必须 | 类型 | 字段描述 | 备注
    |------------- |---------|-----|----------|----
    |client_id     | required|string|客户端id|
    |client_secret | required|string|客户端secret|
    |username      | required|string|登录名|
    |password      | required|string|密码|
    |grant_type    | required|enum|授权类型| 目前只有SSO
    
    > Example for success response
    ```json
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzM5MTExMzksInVzZXJfbmFtZSI6IjIwMTQwMDgwMDUwMSIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6IjBlMjUyNGU1LTc5YzItNGMzNC1iZDk0LTkwMmE3YmQ4ZmQ4YSIsImNsaWVudF9pZCI6IkxNUyIsInNjb3BlIjpbIk9QRU4iXX0.4PUbKn0gs0-vY-JoHea3vzM64XTZJYKK4z-k5IBzfLg",
        "token_type": "bearer",
        "expires_in": 2905,
        "scope": "OPEN",
        "user_name": "201400800501",
        "jti": "0e2524e5-79c2-4c34-bd94-902a7bd8fd8a"
    }
    ```
    > Example for fail response
    ```json
    {
        "error": "invalid_grant",
        "error_description": "Grant fail",
        "code": "102003"
    }
    ```

+ GET   /oauth/check_token

    |字段名        | 是否必须 | 类型 | 字段描述 | 备注
    |------------- |---------|-----|----------|----
    |token         | required|string|accessToken|
    
    > Example for success response
    ```json
    {
        "exp": 1573922325,
        "user_name": "201400800501",
        "authorities": [
            "ADMIN"
        ],
        "jti": "50f2a2a9-e8fe-49d6-8443-73c865c41a8f",
        "client_id": "LMS",
        "scope": [
            "OPEN"
        ]
    }
    ```
    > Example for fail response
    ```json
    {
        "error": "invalid_request",
        "error_description": "Token has expired",
        "code": "999001"
    }
    ```
    

+ POST /logon

    |字段名        | 是否必须 | 类型 | 字段描述 | 备注
    |------------- |---------|-----|----------|----
    |login         | required|string|登录名|不可重复
    |password      | required|string|密码|
    |confirm_password|required|string|确认密码|
    |role             |required|enum|角色|目前支持两种:STUDENT/TEACHER
    |client_id        |required|enum|客户端id|目前支持一种: LMS
    |email            |optional|string|邮箱|未来支持修改密码有用
    
    > Example for success response
    ```json
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzM5MTE4NTMsInVzZXJfbmFtZSI6IjIwMTUwMDgwMDUwMSIsImF1dGhvcml0aWVzIjpbIlNUVURFTlQiXSwianRpIjoiOTk5NzZiZGUtNTM4Ny00MzQwLWJhNzMtYmI1NDYzM2YyYzA4IiwiY2xpZW50X2lkIjoiTE1TIiwic2NvcGUiOlsiT1BFTiJdfQ.4QgWdJaFFUMNpI3o5MJtjdJJXFgdV1U2AMxmROhGq68",
        "token_type": "bearer",
        "expires_in": 3599,
        "scope": "OPEN",
        "jti": "99976bde-5387-4340-ba73-bb54633f2c08"
    }
    ```
    > Example for fail response
    ```json
    {
        "code": 101001,
        "message": "INPUT_FIELD_INVALID"
    }
    ```
  
    
+ GET /v1/users 管理员使用

    |字段名        | 是否必须 | 类型 | 字段描述 | 备注
    |------------- |---------|-----|----------|----
    |login         |optional |string|查询登录名|目前支持全匹配
    |limit         |optional |int   |limit|默认10
    |offset        |optional |int   |offset|默认0
    
    > Example for success response
    ```json
    [
        {
            "id": 1,
            "login": "201400800501",
            "role": "ADMIN",
            "email": "1054646429@qq.com",
            "create_time": 1573565929,
            "update_time": 1573565929,
            "last_grant": [
                {
                    "id": 1,
                    "user_id": 1,
                    "login": "201400800501",
                    "times": 16,
                    "create_time": 1573567899,
                    "update_time": 1573908233
                }
            ]
        },
        {
            "id": 29,
            "login": "201500800501",
            "role": "STUDENT",
            "create_time": 1573908253,
            "update_time": 1573908253,
            "last_grant": [
                {
                    "id": 29,
                    "user_id": 29,
                    "login": "201500800501",
                    "times": 1,
                    "create_time": 1573908253,
                    "update_time": 1573908253
                }
            ]
        }
    ]
    ```
    > Example for fail response
    ```json
    {
        "error": "invalid_token",
        "error_description": "Access token expired: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzM5MTExMzksInVzZXJfbmFtZSI6IjIwMTQwMDgwMDUwMSIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6IjBlMjUyNGU1LTc5YzItNGMzNC1iZDk0LTkwMmE3YmQ4ZmQ4YSIsImNsaWVudF9pZCI6IkxNUyIsInNjb3BlIjpbIk9QRU4iXX0.4PUbKn0gs0-vY-JoHea3vzM64XTZJYKK4z-k5IBzfLg"
    }
    ```
  
+ PUT /v1/users/authorize   管理员使用

    |字段名        | 是否必须 | 类型 | 字段描述 | 备注
    |------------- |---------|-----|----------|----
    |user_id       |required |long|待授权的用户id|
    |role          |required |enum |待授权的角色|
    
    > Example for response
    ```json
    {
        "code": 0
    }
    ```
    > Example for fail response
    ```json
    {
        "code": 103001,
        "message": "RESOURCE_NOT_FOUND: user"
    }
    ```
    授权不会立刻生效  需要重新登录
