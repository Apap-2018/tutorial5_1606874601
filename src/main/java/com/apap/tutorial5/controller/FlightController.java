package com.apap.tutorial5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}

	/*@RequestMapping(value = "/flight/delete/{flightNumber}", method = RequestMethod.GET)
	private String deleteFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber);
		flightService.deleteFlight(flight);
		return "delete";
	}*/
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getFlightNumber());
		}
		return "delete";
	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
        FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber);
        model.addAttribute("flight", flight);
        return "update-flight";
    }

    @RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.POST)
    private String updateFlight(@PathVariable(value = "flightNumber") String flightNumber, @ModelAttribute FlightModel flight) {
        flightService.updateFlight(flightNumber, flight);
        return "update";
    }
    
    @RequestMapping(value= "/flight/view", method = RequestMethod.GET)
	private String viewFlight(@RequestParam("flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber);
		if (flight!=null) {
			model.addAttribute("flight", flight);
			model.addAttribute("pilot", flight.getPilot());
			return "view-flight";
		}
		return "not-found";
	}
}
