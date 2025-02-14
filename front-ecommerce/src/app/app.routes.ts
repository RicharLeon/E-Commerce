import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { ProfileComponent } from './business/profile/profile.component';
import { UpdateuserComponent } from './auth/components/updateuser/updateuser.component';
import { UserlistComponent } from './auth/components/userlist/userlist.component';
import { adminGuard, userGuard } from './auth/guards/auth.guard';
import { PrincipalComponent } from './business/principal/principal.component';
import { ProductOverviewsComponent } from './business/productos/product-overviews/product-overviews.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'profile', component: ProfileComponent, canActivate: [userGuard]},
    {path: 'update/:id', component: UpdateuserComponent},
    {path: 'users', component: UserlistComponent, canActivate:[adminGuard]},
    {path: 'principal', component: PrincipalComponent},
    {path: 'product-overviews', component: ProductOverviewsComponent},
    {path: '**', component: PrincipalComponent},
    {path: '', redirectTo: '/principal', pathMatch: 'full'},
];