package com.rony.springhibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
        @RequestMapping("/")
        @ResponseBody
        public String index(){
            String response = "Bienbenido a mi app de profesores <a href = 'https://platzi.com/clases/1110-jee-2017/7245-creando-controller-api-rest-por-entidad/'>Picale</a>";
            return response;
        }
}
