package mx.com.robertoleon.ciberadmin.domain.login

class Permiso {

    String authority
    static mapping = {
        cache true
    }
    //comentario
    static constraints = {
        authority blank: false, unique: true
    }
}
