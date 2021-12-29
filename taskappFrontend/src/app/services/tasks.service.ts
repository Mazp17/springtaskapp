import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  _url = 'http://127.0.0.1:8080/api/tasks';

  constructor(
    private http: HttpClient,
    private router: Router
    ) { 

    console.log('Servicio de Tasks, listo!');

    // this.CargarInfo(); 
   };

   getTasks() {
    let headers = new HttpHeaders()
      .set('Type-content', 'aplication/json');

    return this.http.get(this._url, {
      headers: headers
    });

   };

   
}


/* private CargarInfo() {
    //leer archivo JSON
    this.http.get('assets/data/task.json')
    .subscribe( (resp: Tasks) => {


      this.cargada = true;
      this.info = resp;

      
      console.log(resp);

    });
   } */
