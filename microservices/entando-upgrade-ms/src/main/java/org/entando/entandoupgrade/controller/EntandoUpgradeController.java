package org.entando.entandoupgrade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

class Version {
    @JsonProperty("id")
    private Number id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("html_url")
    private String url;
    @JsonProperty("published_at")
    private Date date;

    public Version() {
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date;
    }
}

@Controller
public class EntandoUpgradeController {
    final String RELEASES_URL = "https://api.github.com/repos/entando/entando-releases/releases";

    @ResponseBody
    @RequestMapping(path = "/versions", method = GET, produces = "application/json")
    public List<Object> fullVersions() {
        RestTemplate restTemplate = new RestTemplate();

        Object[] versions = restTemplate.getForObject(RELEASES_URL, Object[].class);

        return Arrays.asList(versions);
    }

    @ResponseBody
    @RequestMapping(path = "/versions/short", method = GET, produces = "application/json")
    public List<Version> shortVersions() {
        final String regex = "v[0-9].[0-9].[0-9]$";
        final Pattern pattern = Pattern.compile(regex);

        RestTemplate restTemplate = new RestTemplate();

        Version[] versions = restTemplate.getForObject(RELEASES_URL, Version[].class);
        List<Version> ret_versions = Arrays.asList(versions).stream().filter(
                v -> (pattern.matcher(v.getName()).find())).sorted((v1, v2) -> v2.getDate().compareTo(v1.getDate()))
                .collect(Collectors.toList());

        return ret_versions;
    }
}
