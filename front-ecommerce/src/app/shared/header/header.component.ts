import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../auth/services/users.service';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../business/service/carrito.service';
import { CarritoInterface } from '../../models/carrito';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  carritoExistente: any;
  idUser: any;
  profileInfo: any;
  userId: number | null = null;
  loading = false;
  productId!: number;
  producto: any;


  errorMessage: string | undefined;
  newCarrito: CarritoInterface = {
    idUsuario: 0
  }; // Initialize with appropriate default values


  constructor(private readonly userService: UsersService,
    private readonly carritoService: CarritoService,
    private readonly usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  isAuthenticated: boolean = false;
  isAdmin: boolean = false;
  isUser: boolean = false;

  async ngOnInit(): Promise<void> {

    this.userService.isAuthenticated$.subscribe(isAuthenticated => {
      this.isAuthenticated = isAuthenticated;
    });
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();
    console.log("adute", this.isAuthenticated);
    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error("No Token Found")
      }

      this.profileInfo = await this.usersService.getYourProfile(token);
      this.idUser = this.profileInfo.ourUsers.id;
    } catch (error: any) {
      this.showError(error.message)
    }

  }
  showError(message: any) {
    throw new Error('Method not implemented.');
  }

  logout() {
    this.userService.logOut();

  }


  //Navegacion al carrito

  goToShoppingCard() {
    this.validarExistenciaCarrito();
  }

  validarExistenciaCarrito() {
    this.carritoService.getCarritoById(this.idUser).subscribe({
      next: (data) => {

        console.log("esta es la data", data);
        this.carritoExistente = data;
        if (!data || Object.keys(data).length === 0) {

          console.log("no existe carrito",  Object.keys(data).length === 0);

          this.createNewCarrito();
          return;
        } else {

          this.router.navigate(['/shopping-card/' + data.idCarrito]);
        }
      },
      error: (error) => {
        console.error('Error al validar la existencia del carrito:', error);
      }
    });
  }


  createNewCarrito() {

    this.newCarrito.idUsuario = this.idUser;

    this.carritoService.addNewCarrito(this.newCarrito).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }





}
