import { HttpErrorResponse } from '@angular/common/http';
import { ThrowStmt } from '@angular/compiler';
import { Component, ElementRef, Injectable, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { Tasks } from 'src/app/interfaces/tasks.interface';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { EventEmitter } from '@angular/core';
import Swal from 'sweetalert2';

//TODO: Implementar metodos para registro de nuevas tareas.
@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class TasksComponent{

  title = 'task';
  public tasks: Array<Tasks> = []; 
  public tasksDeleted: Array<Tasks> = []; 
  
  public errorMio: any;

  public newTaskForm: FormGroup;

  public submit!: boolean;
  public notTasks!: boolean;

  @Output() childToParent: EventEmitter<any> = new EventEmitter();

  @ViewChild('addTask') modal!: ElementRef;

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
  /*
  sendToParent(task: any) {
    this.childToParent.emit(task);
  }*/

  


  constructor(private apiService: ApiService, 
    private modalService:NgbModal,
    public authService: AuthService,
    public fb: FormBuilder,
    public router: Router) {
      /* this.newTask = this.fb.group({
        nameTask: ['', [Validators.required]],
        task: ['', [Validators.required]]
      }); */

      this.newTaskForm = this.fb.group({
        nameTask: ['', [Validators.required]],
        task: ['', Validators.required]
      })
      this.submit = false;
    }

    get f() {
      return this.newTaskForm.controls;
    }

   ngOnInit(): void {
     this.notTasks = false;
    this.getTask();
  }

   openModal(modal: ElementRef){
     this.modalService.open(modal);
   }

   closeModal() {
      this.modalService.dismissAll();
   }
   

   getTask() {
    this.apiService
    .getTasks()
    .subscribe((resp: any) => {
      let tasks: Array<Tasks> = resp.tasks;
      this.tasks = [];
      this.tasksDeleted = [];
      for(let i = 0; i < tasks.length; i++) {
        if(tasks[i].deleted){
          this.tasksDeleted.push(tasks[i]);
        } else {
          this.tasks.push(tasks[i]);
        }
      }
      if(tasks.length == 0) {
        this.notTasks = true;
      }      
    },
  (err: HttpErrorResponse) => {
    if(err.error instanceof Error) {
      console.log('Client-side error');

    } else {
      console.error(err);
      this.errorMio = "El servidor no responde, puede ser que este apagado. Comuniquese con el administrador del servicio.";
    }
  });
   }


   saveTask(data: Tasks) {
     this.apiService.saveTask({
        task: data.task,
        nameTask: data.nameTask
     }).subscribe(
       () => {
         this.closeModal();
         this.Toast.fire({
           icon: 'success',
           title: 'Task added!'
         })
        this.submit = true;
        this.getTask();
       },
       (error) => {
         console.error(error);
       }
    )
  }

  public openOptions = (id: any, completed: any): void => {
    let completedString = this.completedString(completed, 'swal');
    const swalwithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn mx-2 mt-2 ' + this.completedClass(completed),
        denyButton: 'btn btn-info me-2 mt-2',
        cancelButton: 'btn btn-danger me-3 mt-2 '
      },
      buttonsStyling: false
    });

    swalwithBootstrapButtons.fire({
      title: '¿Que quieres hacer?',
      icon: 'info',
      showDenyButton: true,
      showCancelButton: true,
      cancelButtonText: 'Eliminar tarea',
      denyButtonText: 'Editar esta tarea',
      confirmButtonText: completedString
    }).then((result) => {
      if(result.isConfirmed) {
        this.completedTask(id, completed);
      } else if (result.isDenied) {
        this.router.navigate(['/task/' + id]);
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        this.deletedTaskSwal(id);
      }
    }); 
  }

  completedTask(id: number, completed: any) {
    this.apiService.completedTask(id).subscribe(
      () => {
        if(completed) {
          this.Toast.fire({
            icon: 'warning',
            title: 'No completada!',
            text: this.completedString(completed, 'toast')
          });
        } else {
          this.Toast.fire({
            icon: 'success',
            title: 'Completada!',
            text: this.completedString(completed, 'toast')
          });
        }
        this.getTask();
      }, 
      (error) => {
        console.error(error);
      }
    )
  }

  deletedTaskSwal(id: any) {
    Swal.fire({
      title: '¿Estas seguro de querer eliminar esta tarea?', 
      text: 'Cuando eliminas una tarea, esta pasa temporalmente al apartado de eliminados.',
      icon: 'warning',
      showCancelButton: true,
      reverseButtons: true,
      cancelButtonText: 'No, salir',
      confirmButtonText: 'Si, quiero eliminarla',
      confirmButtonColor: '#198754',
      cancelButtonColor: '#dc3545'
    }).then((result) => {
      if(result.isConfirmed) {
        this.deletedTask(id);
      }
    })
  }

  deletedTask(id: any) {
    this.apiService.deletedTask(id).subscribe(
      () => {
        this.Toast.fire({
          icon: 'warning',
          title: 'Eliminado!',
          text: 'Has eliminado una tarea! ahora puedes encontrarla en la papelera'
        })
        this.getTask();
      },
      (error) => {
        console.error(error);
      }
    )
  }

  completedString(completed: any, type: String): string {
    switch(type) {
      case 'swal': {
        if(completed) {
          return 'Marcar como no terminada';
        }
        if(!completed) {
          return 'Marcar como completada';
        }
        break;
      }
      case 'toast': {
        if(completed) {
          return 'Has marcado una tarea como no terminada!';
        }
        if(!completed) {
          return 'Has marcado una tarea como terminada, felicidades!';
        }
      }
    }
    return 'null';
  }

  completedClass(completed: any) {
    if(completed) {
      return 'btn-warning';
    } else if(!completed) {
      return 'btn-success';
    } else {
      return ' ';
    }
    
  }

  

}

