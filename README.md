# decryptor-starter

Dependency
-> spring-boot-starter-aop
-> spring-boot-autoconfigure
-> lombok

this project use AOP for decrypt value from methoed that mark `@DecryptValue`

Example
@Data
public class UserInfo {
  @Encrypt
  private String firstName;
  private String lastName;
}

.
.
.

@PostMapping
@DecryptValue
public UserInfo(@RequestBody UserInfo userInfo){
  return userInfo;
}

assume that firstName is encrypted, then this advicer will decrypt all field that mark `@Encrypt`
Ps. this starter support Model inside Model by checking classLoader

