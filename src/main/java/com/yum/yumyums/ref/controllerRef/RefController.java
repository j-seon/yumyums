package com.yum.yumyums.ref.controllerRef;


import com.yum.yumyums.ref.dtoRef.RefDTO;
import com.yum.yumyums.ref.dtoRef.RefTemplateData;
import com.yum.yumyums.ref.serviceRef.RefService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ref")
public class RefController {
    private final RefService refService;

    @ModelAttribute
    RefTemplateData tempData(RefTemplateData refTemplateData, HttpServletRequest request){
        String uri = request.getRequestURI();
        String service = uri.substring(uri.lastIndexOf("/")+1);
        System.out.println("refTemplateData.service = " + service);

        refTemplateData.setCate("ref");
        refTemplateData.setService(service);
        System.out.println("refTemplateData = " + refTemplateData);

        return refTemplateData;
    }

    @RequestMapping(value = "{service}", method = RequestMethod.GET)
    public String refServiceGet(Model model, RefTemplateData refTemplateData){
        System.out.println("before setCate = " + refTemplateData.getCate());
        refTemplateData.setCate("ref");
        System.out.println("after setCate = " + refTemplateData.getCate());
        switch(refTemplateData.getService()){
            case "list":
                List<RefDTO> refDTOList = refService.findAll();
                model.addAttribute("refList", refDTOList);
            case "save":
        }
        return "templateRef";
    }

    @RequestMapping(value = "{service}", method = RequestMethod.POST)
    public String refServicePost(Model model, RefDTO refDTO, RefTemplateData refTemplateData){
        System.out.println("refDTO = " + refDTO);
        switch(refTemplateData.getService()){
            case "save":
                refService.save(refDTO);
                refTemplateData.setMsg("등록되었습니다.");
                refTemplateData.setGoUrl("/ref/list");
                break;
            case "update":
                RefDTO ref = refService.update(refDTO);
                model.addAttribute("ref", ref);
                refTemplateData.setMsg("수정되었습니다.");
                refTemplateData.setGoUrl("/ref/detail/"+refDTO.getId());
                break;
            }
        return "inc/alertRef";
    }

    @RequestMapping("{service}/{id}")
    public String serviceId(Model model,RefTemplateData refTemplateData, @PathVariable Long id){
        System.out.println(" serviceId = " +refTemplateData.getCate()+"/"+ refTemplateData.getService() + ", id = " + id);
        RefDTO refDTO = refService.findById(id);

        switch(refTemplateData.getService()){
            case "detail":
                refService.updateHits(id);
                model.addAttribute("ref", refDTO);
                break;
            case "update":
                model.addAttribute("refUpdate", refDTO);
                break;
            case "delete":
                refService.delete(id);
                refTemplateData.setMsg("삭제되었습니다.");
                refTemplateData.setGoUrl("/ref/list");
                return "inc/alertRef";
        }
        return "templateRef";
    }

}
