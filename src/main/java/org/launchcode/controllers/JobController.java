package org.launchcode.controllers;

import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.launchcode.models.CoreCompetency;
import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.Location;
import org.launchcode.models.PositionType;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

    	Job idJob = jobData.findById(id);
    	model.addAttribute("idJob", idJob);
    	
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@Valid JobForm jobForm, Errors errors, Model model) {

    	if (errors.hasErrors()) {
    		return "new-job";
    	} 
    	
    	String name = jobForm.getName();
        Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location location = jobData.getLocations().findById(jobForm.getLocationsId());
        PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypesId());
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());

        Job newJob = new Job(name, employer, location, positionType, coreCompetency);
        jobData.add(newJob);

        int newJobId = newJob.getId();

        return "redirect:?id=" + newJobId;

    }
}
