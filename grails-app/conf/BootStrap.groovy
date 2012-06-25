import mx.com.robertoleon.ciberadmin.domain.login.Usuario
import mx.com.robertoleon.ciberadmin.domain.login.Permiso
import mx.com.robertoleon.ciberadmin.domain.login.UsuarioPermiso

class BootStrap {

    def init = { servletContext ->

        if (!Permiso.count()){

        //definimos nuestra lista de roles
                def rolAdmin = new Permiso(authority: 'ROLE_ADMIN').save(failOnError: true)
                def rolUsuario = new Permiso(authority: 'ROLE_USUARIO').save(failOnError: true)

        //creamos un usuario de prueba
                def testUser = new Usuario(
                        nombre:"Roberto",
                        apellidoPaterno:"Leon",
                        apellidoMaterno:"Cruz",
                        direccion: "mexico df",

                        username:"robe",
                        password:"admin",
                        enabled:true,
                        accountExpired:false,
                        accountLocked:false,
                        passwordExpired:false
                )

                testUser.save(failOnError:true)
                UsuarioPermiso.create testUser, rolAdmin, true

            }
        }


    def destroy = {
    }
}
