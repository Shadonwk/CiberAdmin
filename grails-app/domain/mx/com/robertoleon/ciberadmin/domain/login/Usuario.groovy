package mx.com.robertoleon.ciberadmin.domain.login

class Usuario {

    //van de cajon son requeridas por spring
    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    //requeridas por nuestro sistema opcionales
    String nombre
    String apellidoPaterno
    String apellidoMaterno
    String direccion

    def springSecurityService

    static constraints = {
        username blank: false, unique: true
        password blank: false
        nombre blank: false, maxSize:50
        apellidoPaterno blank: false, maxSize:50
        apellidoMaterno blank: false, maxSize:50
    }

    static mapping = {
        password column: '`password`'
    }


    Set getAuthorities() {
        UsuarioPermiso.findAllByUsuario(this).collect { it.permiso } as Set
    }

//antes de guardar un nuevo usuario codifica el pass
    def beforeInsert() {
        encodePassword()
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

}
