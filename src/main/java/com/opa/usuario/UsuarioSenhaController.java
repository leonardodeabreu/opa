package com.opa.usuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UsuarioSenhaController.MAPPING)
public class UsuarioSenhaController {

    public static final String MAPPING = "/usuariosenha";
	
    @Autowired
	private UsuarioService usuarioService;
    
    @Autowired
	private JavaMailSender JavaMailSender;
    
   
    public UsuarioSenhaController(JavaMailSender JavaMailSender){
    	this.JavaMailSender = JavaMailSender;
    	
    }
		
	@RequestMapping(method = RequestMethod.POST)
	public String recuperarSenha(@RequestBody Map<String, String> data) {
		
	   String email = data.get("email");		
	  
	   String emailUsuario = usuarioService.getEmails(email);
	  
	  	   	   
	   String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

       String senha="";


	   for (int x=0; x<10; x++){
	        int j = (int) (Math.random()*carct.length);
	        senha += carct[j];
	   }
	   
	   Usuario user = usuarioService.getUsuarioTrocaSenha(emailUsuario);
	   
	   user.setSenha(senha);
	   
	   usuarioService.salvar(user);
	   
	   SimpleMailMessage mail = new SimpleMailMessage();
	   
	   mail.setTo(emailUsuario);
	   mail.setFrom("time08escoladeti@gmail.com");
	   mail.setSubject("Ei, sua senha do OPA chegou");
	   mail.setText("Sua nova senha+: "+senha);
	   
	   JavaMailSender.send(mail);
	   
	   return "{\"Email\""+email+"\"}";
		
	  
	}
}


