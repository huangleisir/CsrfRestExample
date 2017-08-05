package sk.bsmk.csrf;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SecuredController {

    public static final String DEFAULT_INFO = "Default info";
    public static final String LOGGED_MESSAGE = "logged in";

    private String info = DEFAULT_INFO;

    @ResponseBody
    @RequestMapping(value = "/info")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public ResponseEntity<String> login(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(token.getHeaderName(), token.getToken());
        return new ResponseEntity<>(LOGGED_MESSAGE, headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<String> post(@RequestBody String newInfo) {
        info = newInfo;
        return new ResponseEntity<>("info updated", HttpStatus.OK);
    }
    
    @ResponseBody
    @RequestMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest  req) {
    	HttpSession session =  req.getSession();
    	session.invalidate();
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

}
