import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import Swal from 'sweetalert2';
import { Task } from '../pages/tasks/task/task.model';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-trash',
  templateUrl: './trash.component.html',
  styleUrls: ['./trash.component.css']
})
export class TrashComponent implements OnInit {

  public tasksDeleted: Array<Task> = [];
  public tasksDeletedNull!: boolean;
  
  @Output() childToParent: EventEmitter<any> = new EventEmitter();

  constructor(public apiService: ApiService) { }

  ngOnInit(): void {
    this.tasksDeletedNull = false;
    this.getTasksDeleted();    
  }

  public Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    }
  });

  getTasksDeleted() {
    this.apiService.getTasks().subscribe(
      (resp: any) => {
        let tasks: Array<Task> = resp.tasks;
        for(let i = 0; i < tasks.length; i++) {
          if(tasks[i].deleted){
            this.tasksDeleted.push(tasks[i]);
          }
        }

        if(this.tasksDeleted.length == 0) {
          this.tasksDeletedNull = true;
        }
      },
      (error) => {
        console.error(error);
        
      }
    )
  }

  public openOptions = (id: any): void => {
    const swalwithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn mx-2 btn-warning',
        cancelButton: 'btn btn-success me-3 '
      },
      buttonsStyling: false
    });
    swalwithBootstrapButtons.fire({
      title: '¿Deshacer borrado de esta tarea?',
      text: 'Cuando deshaces el borrado de una tarea, esta regresa al apartado principal.',
      icon: 'info',
      showCancelButton: true,
      confirmButtonText: 'Si, quiero deshacerlo',
      cancelButtonText: 'No, quiero salir'
    }).then((result) => {
      if(result.isConfirmed) {
        this.apiService.deletedTask(id).subscribe(
          () => {
            this.Toast.fire({
              icon: 'warning',
              title: 'Eliminado!',
              text: 'Has eliminado una tarea! ahora puedes encontrarla en la papelera'
            });
            this.tasksDeleted = [];
            this.getTasksDeleted();
          }, 
          (error: HttpErrorResponse) => {
            console.error(error);
          }
        )

      }
    })
  }


  

}
