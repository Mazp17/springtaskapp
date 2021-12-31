import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  _urlBase = 'http://localhost:8080/api';

  
  constructor(private http: HttpClient,
    public authService: AuthService) {}

  getTasks(user: any) {
    console.log(user);
  return this.http.post(this._urlBase + '/task', user).pipe(
    catchError(e => {
      return throwError(e);
    })
  );
  }

  registerUser(user: any){
    return this.http.post(this._urlBase + '/user/register/1', user);
  }

  
  /*
  private IsNotAuthorization(e: any): boolean { 
    if(e.status==401) {
      if(this.authService.isAuthenticated()) {
        this.authService.logout();
      } 
      this.router.navigate(["/login"]);
      return true;
    } else {
      return false;
    }
  }*/
}


