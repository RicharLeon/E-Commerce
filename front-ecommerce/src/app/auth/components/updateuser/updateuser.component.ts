import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Users } from '../../../models/users';

@Component({
  selector: 'app-updateuser',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './updateuser.component.html',
  styleUrl: './updateuser.component.css'
})
export class UpdateuserComponent implements OnInit {

  usersForm: FormGroup;

  constructor(private fb: FormBuilder, private readonly userService:UsersService,
    private readonly router: Router,
    private readonly route:ActivatedRoute){

      this.usersForm = this.fb.group({
        name: ['',],
        email: ['',],
        role: ['',],
        city: ['',]
      });
    }


    userId: any;
    userData: any = {}
    errorMessage:string = ''


  ngOnInit(): void {
    this.getUserById()
      console.log(this.userId)
  }

  async getUserById(){
    this.userId = this.route.snapshot.paramMap.get('id');
    const token = localStorage.getItem('token');
    if (!this.userId || !token) {
      this.showError("User ID or Token is required");
      return;
    }
  
    try {
      let userDataResponse = await this.userService.getUsersById(this.userId, token);
      const { roles } = userDataResponse.ourUsers; // Ajusta la desestructuración según lo que recibas
  
      console.log("roles:", roles); // Aquí se mostrará el array de roles
      console.log(userDataResponse); // Aquí se mostrará la ciudad
  
      this.usersForm.patchValue({
        name: userDataResponse.ourUsers.name, // Asumiendo que username es lo que deseas mostrar como name
        email: userDataResponse.ourUsers.email,
        role: roles,   // Asegúrate que el control del formulario acepte un array o lo conviertas a string si es necesario
        city: userDataResponse.ourUsers.city
      });
  
    } catch (error: any) {
      this.showError(error.message);
    }
  }
  

  async updateUser(){
    const confitm = confirm("Are you sure you wanna update this user")
    if(!confirm) return
    try{
      const token = localStorage.getItem('token')
      if(!token){
        throw new Error("Token not found")
      }

      const updateProducut: Users = {
        name: this.usersForm.value.name,
        email: this.usersForm.value.email,
        city: this.usersForm.value.city,
        role: this.usersForm.value.role
      }



      const res = await this.userService.updateUSer(this.userId, updateProducut, token);
      console.log(res)

      if(res.statusCode === 200){
        this.router.navigate(['/auth-list'])
      }else{
        this.showError(res.message)
      }

    }catch(error:any){
      this.showError(error.message)
    }

  }


  showError(mess: string) {
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}
