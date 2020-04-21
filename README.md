# decryptor-starter

Dependency
* spring-boot-starter-aop
* spring-boot-autoconfigure
* lombok

This project use AOP for decrypt value from methoed that mark `@DecryptValue`

Example

```java
@Data
public class UserInfo {
  @Encrypt
  private String firstName;
  private String lastName;
}
```


Controller
```java
@PostMapping
@DecryptValue
public UserInfo test(@RequestBody UserInfo userInfo){
  return userInfo;
}
```
Assume that firstName is encrypted, then this advicer will decrypt all field that mark `@Encrypt`


Ps. this starter support Model inside Model by checking classLoader.


- [x] Detect filed was encrypt and model in model
- [x] Modify argument and execute by using `@Around`
- [ ] decrypt with java cipher
- [ ] support List
