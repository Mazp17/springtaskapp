import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@Angular/router';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  _urlBase = 'http://localhost:8080/api';
  
  constructor(private http: HttpClient, private router: Router) {
   }

   getTasks() {
     let headers = new HttpHeaders()
      .set('Type-content', 'aplication/json');
    
    return this.http.get(this._urlBase + '/task', {
      headers: headers 
    }).pipe(
      catchError(e => {
        this.IsNotAuthorization(e);
        return throwError(e);
      })
    );
   }

   registerUser(user: any){

     return this.http.post(this._urlBase + '/user/register/1', user);
   }
  
   private IsNotAuthorization(e: any): boolean { 
    if(e.status==401) {
      //this.route.(['/login']);
      return true;
    }
    return false;
  }
}


