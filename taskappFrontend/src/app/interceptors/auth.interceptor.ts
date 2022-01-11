import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { AuthService } from "../services/auth.service";

@Injectable()
//Esta clase, se encarga de interceptar las peticiones y las maneja segun su error
// Si el backend envia un error 401, lo redirecciona al login. 
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService,
    private router: Router) {}

  //funcion que intercepta todas las peticiones enviadas al backend
  intercept(req: HttpRequest<any>, next: HttpHandler):
  Observable<HttpEvent<any>> { 


    return next.handle(req).pipe(
      catchError(e => {
        if(e.error.code == "2001") {
          this.router.navigate(['/home']);
        }
        if(e.status == 401) {
          if(this.authService.isAuthenticated()) {
            this.authService.logout();
          }
          this.router.navigate(['/login']);
        }
        return throwError(e);
      })
    );
  }
}
