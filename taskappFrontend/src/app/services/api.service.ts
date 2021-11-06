import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  _urlBase = 'http://localhost:8080/api';

  constructor( private http: HttpClient) {
    console.log('Servicio de API listo!');
   }

   getTasks() {
     let headers = new HttpHeaders()
      .set('Type-content', 'aplication/json');
    
    return this.http.get(this._urlBase + '/task', {
      headers: headers
    });
   }
}
