import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { TaskComponent } from './pages/tasks/task/task.component';


import { TasksComponent } from './pages/tasks/tasks.component';
import { UserComponent } from './pages/user/user.component';
import { TrashComponent } from './trash/trash.component';


const routes: Routes = [
  {path: 'home', component: TasksComponent, canActivate: [AuthGuard]},
  {path: 'user', component: UserComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'task/:idTask', component: TaskComponent},
  {path: 'trash', component: TrashComponent},


  {path: '**', pathMatch: 'full', redirectTo: 'home'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
