import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from 'src/app/services/api.service';
//TODO: Implementar metodos para registro de nuevas tareas.
@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent{

  title = 'task';
  public tasks: Array<any> = []; 
  public errorMio: any;

  constructor(private apiService: ApiService, private modal:NgbModal) {
      this.apiService
      .getTasks()
      .subscribe((resp: any) => {
      this.tasks = resp;  
    },
    (err: HttpErrorResponse) => {
      if(err.error instanceof Error) {
        console.log('Client-side error');

      } else {
        this.errorMio = "El servidor no responde, puede ser que este apagado. Comuniquese con el administrador del servicio.";
      }
    }
    );
   }

   OpenCenter(contenido: any){
     this.modal.open(contenido);
   }

   
}

