package com.skilldistillery.doggiemeetup.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.doggiemeetup.entities.Dog;
import com.skilldistillery.doggiemeetup.entities.Meetup;
import com.skilldistillery.doggiemeetup.services.MeetupService;


@CrossOrigin({"*", "http://localhost:4210" })
@RequestMapping("api")
@RestController
public class MeetupController {
	
	@Autowired
	private MeetupService meetupService;
	
	
	@GetMapping("meetups/")
	public List<Meetup> index(
			HttpServletRequest req,
			HttpServletResponse res
			) {
		List<Meetup> meetups = meetupService.index();
		if(meetups == null) {
			res.setStatus(404);
		}
		return meetups;
	}
	
	@GetMapping("dogParks/{dogParkId}/meetups/")
	public List<Meetup> indexByDogPark(
			HttpServletRequest req,
			HttpServletResponse res, 
			@PathVariable int dogParkId
			) {
		List<Meetup> meetups = meetupService.indexByDogParkId(dogParkId);
		if(meetups == null) {
			res.setStatus(404);
		}
		return meetups;
	}
	
//| `Meetup`|`GET api/meetups/{id}`| Gets one meetup by id|
	@GetMapping("meetups/{id}")
	public Meetup show(
			HttpServletRequest req,
			HttpServletResponse res, 
			@PathVariable int id
			) {
		Meetup meetups = meetupService.show(id);
		if(meetups == null) {
			res.setStatus(404);
		}
		return meetups;
	}
	@GetMapping("meetups/{id}/dogs")
	public List<Dog> getDogsForMeetup(
			HttpServletRequest req,
			HttpServletResponse res, 
			@PathVariable int id
			) {
		Meetup meetup = meetupService.show(id);
		if(meetup == null) {
			res.setStatus(404);
		}
		List<Dog> dogs = null;
		dogs = meetupService.getDogsByMeetup(id);
		return dogs;
	}
	
//| `Meetup`|`POST api/meetups`| Creates a new meetup|
	@PostMapping("auth/meetups")
	public Meetup create(
			HttpServletRequest req,
			HttpServletResponse res,
			@RequestBody Meetup meetup,
			Principal principal
			) {
		
		try {
			meetup = meetupService.create(principal.getName(),meetup);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			url.append("/").append(meetup.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			res.setStatus(400);
			meetup = null;
		}
		return meetup;
	}
		
	
//| `Meetup`|`PUT api/meetups/{id}`| Replaces an existing meetup by id|
	@PutMapping("auth/meetups/{id}")
	public Meetup update(
			HttpServletRequest req,
			HttpServletResponse res,
			@PathVariable int id,
			@RequestBody Meetup meetup,
			Principal principal 
			) {
		try {
			meetup = meetupService.update(principal.getName(),meetup, id);
			if(meetup == null) {
				res.setStatus(404);
			}
			
		} catch (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			meetup = null;
		}
		return meetup;
	}
	
	@PutMapping("auth/meetups/{id}/join/{dogId}")
	public Meetup join(
			HttpServletRequest req,
			HttpServletResponse res,
			@PathVariable int id,
			@PathVariable int dogId,
			@RequestBody Meetup meetup 
			) {
		System.out.println("----------------------------");
		System.out.println(meetup);
		System.out.println("----------------------------");
		try {
			meetup = meetupService.join(meetup, id, dogId);
			if(meetup == null) {
				res.setStatus(404);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			meetup = null;
		}
		return meetup;
	}
	
//| `void`|`DELETE api/meetups/{id}`| Deletes an existing meetup by id|
	@DeleteMapping("auth/meetups/{id}")
	public void destroy(
			HttpServletRequest req,
			HttpServletResponse res,
			@PathVariable int id,
			Principal principal
			) {
		try {
			boolean deleted = meetupService.delete(principal.getName(),id);
			if(deleted) {
				res.setStatus(204);
			}else {
				res.setStatus(404);
			}
		} catch (Exception e) {
			res.setStatus(400);
		}
	}
		
	
//|`List<Meetup>`|`GET api/meetups/search/{keyword}`|Gets meetups with matching keyword|
@GetMapping("meetups/search/{keyword}")
public List<Meetup> getMeetupsFromKeyword(@PathVariable String keyword){
	return null;
}
	

}
