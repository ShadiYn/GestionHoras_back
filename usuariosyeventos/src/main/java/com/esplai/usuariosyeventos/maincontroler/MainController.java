package com.esplai.usuariosyeventos.maincontroler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esplai.usuariosyeventos.dtos.RegistroDTO;
import com.esplai.usuariosyeventos.models.Evento;
import com.esplai.usuariosyeventos.models.Usuario;
import com.esplai.usuariosyeventos.repository.EventoRepository;
import com.esplai.usuariosyeventos.repository.UsuarioEventoRepository;
import com.esplai.usuariosyeventos.repository.UsuarioRepository;
import com.esplai.usuariosyeventos.utils.EventoUtils;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin // Para hacer peticiones desde otro servidor
@RestController // Para hacer peticiones REST
@RequestMapping("/cosas")
public class MainController {

	@Autowired
	private EventoRepository eventoRepository;
	//@Autowired
	private EventoUtils eventoUtils;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioEventoRepository usuarioEventoRepository;
	@Autowired
	private BCryptPasswordEncoder b;
	// @Autowired
	// private ObjetoRepository objetoRepository;

	@PostMapping(path = "/login")
	public ResponseEntity<String> basicauth(UsernamePasswordAuthenticationToken upa) {
		// El objeto upa tiene información sobre el usuario y la contraseña
		// Si el login ha sido exitoso, a partir de ese momento, desde el front,
		// mandaremos en la cabecera de cada petición el username y password que han
		// provocado que el login sea exitoso
		Usuario u = (Usuario) upa.getPrincipal(); // Si en IntelliJ nos da un error cannot find symbol, significa que no
													// esta pillando el getter de la id
		return ResponseEntity.ok().body("{\"resp\":\"Login exitoso\", \"id\":" + u.getId() + "}");

	}

	@PostMapping(path = "/register")
	public String register(@RequestBody RegistroDTO registroDTO) {
		List<Usuario> people = usuarioRepository.findAll();
		for (Usuario usr : people) {
			if (usr.getUsername().equals(registroDTO.getUsername())) {
				return "Usuario repetido";
			}
		}

		Usuario user = new Usuario(registroDTO.getUsername(), registroDTO.getNombre(),
				b.encode(registroDTO.getPassword()));
		usuarioRepository.save(user);
		return "Registro exitoso";
	}

	// Se puede separar pero aqui dependiendo del booleano que recibe puede entrar o
	// salir del evento
	@PostMapping("/joinLeaveEvent")
	public void joinLeaveEvent(@RequestBody int id, String eventName, Boolean inside) {
		Usuario usuario = usuarioRepository.findById(id);
		Evento evento = eventoRepository.findByNombre(eventName);

		if (!inside) {
			if (usuario != null && evento != null) {
				eventoUtils.AddParticipante(evento, usuario);
			}
		} else {
			if (usuario != null && evento != null) {
				eventoUtils.RemoveParticipante(evento, usuario);
			}
		}
		eventoRepository.save(evento);
	}

	@GetMapping("/checkAllEvents")
	public List<Evento> checkAllEvents() {
		return eventoRepository.findAll();
	}

	// Comprobar si estas dentro del evento
	@GetMapping("/checkEvents")
	public boolean checkEvents(@RequestBody int id, String eventName) {
		Usuario usuario = usuarioRepository.findById(id);
		Evento evento = eventoRepository.findByNombre(eventName);
		if (eventoUtils.checkInsideParticipant(evento, usuario)) {
			return true;
		}
		return false;
	}

	@PostMapping(path = "/createEvent")
	public boolean createEvent(@RequestBody String name, LocalDate date, String place, String desc) {
		Evento ev = new Evento(name, date, place, desc); // esto es debug por si falla
		System.out.println(ev);
		eventoRepository.save(ev);
		return true;
	}

	@GetMapping("/getEventById")
	public Evento getEventById(@RequestBody int id) {
		return eventoRepository.findById(id);
	}
	// edit Event
	@PostMapping(path = "/editEvent")
	public boolean editEvent(@RequestBody Evento evento) {
		Evento ev = evento;
		System.out.println(ev);
		eventoRepository.save(ev);
		return true;
	}

	/*
	 * @DeleteMapping("/delete/{id}") public void deleteLibro(@PathVariable("id")
	 * Integer id) { Libro i = new Libro(); i.setLibroId(id);
	 * libroRepository.delete(i); }
	 * 
	 * @GetMapping("/libros") public List<Libro> getLibros() { List<Libro> people =
	 * libroRepository.findAll(); return people; }
	 * 
	 * @GetMapping("/libros/{id}") public Libro getLibrosById(@PathVariable("id")
	 * Integer id) { Libro juga = libroRepository.findLibroByLibroId(id); return
	 * juga; }
	 * 
	 * @PutMapping("/update") public void updateLibro(@RequestBody Libro libro) {
	 * libro.setLibroId(libro.getLibroId()); libroRepository.save(libro); }
	 * 
	 */
}