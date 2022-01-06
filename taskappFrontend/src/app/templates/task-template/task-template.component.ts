import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Tasks } from 'src/app/interfaces/tasks.interface';
import { TasksComponent } from 'src/app/pages/tasks/tasks.component';
import { DatesFunctions } from 'src/functions/datesFunctions';

@Component({
  selector: 'app-task-template',
  templateUrl: './task-template.component.html',
  styleUrls: ['./task-template.component.css']
})
export class TaskTemplateComponent implements OnInit {

  @Input() task : any;
  @Input() clickCardFunction!: (id: number, completed: any) => any;


  constructor(public datesFunctions: DatesFunctions,
    public tasksComponent: TasksComponent
    ) { }

  ngOnInit(): void {
  }

  


}
