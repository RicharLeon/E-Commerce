import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { ProfileComponent } from './business/profile/profile.component';
import { UpdateuserComponent } from './auth/components/updateuser/updateuser.component';
import { UserlistComponent } from './auth/components/userlist/userlist.component';
import { adminGuard, userGuard } from './auth/guards/auth.guard';
import { PrincipalComponent } from './business/principal/principal.component';
import { ProductOverviewsComponent } from './business/productos/product-overviews/product-overviews.component';
import { ProductListComponent } from './business/productos/product-list/product-list.component';
import { ProductsTableComponent } from './admin/products/products-table/products-table.component';
import { ProductsCreateComponent } from './admin/products/products-create/products-create.component';
import { CategoryListComponent } from './admin/categorys/category-list/category-list.component';
import { CategoryCreateComponent } from './admin/categorys/category-create/category-create.component';
import { CategoryEditComponent } from './admin/categorys/category-edit/category-edit.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'profile', component: ProfileComponent, canActivate: [userGuard]},
    {path: 'update/:id', component: UpdateuserComponent},
    {path: 'users', component: UserlistComponent, canActivate:[adminGuard]},
    {path: 'principal', component: PrincipalComponent},
    {path: 'product-overviews', component: ProductOverviewsComponent},
    {path: 'productos-list', component: ProductsTableComponent},
    {path: 'categoria-list', component: CategoryListComponent},
    {path: 'auth-list', component: UserlistComponent},
    {path: 'new-producto', component: ProductsCreateComponent},
    {path: 'new-category', component: CategoryCreateComponent},
//rutas editar
    {path: 'edit-category/:id', component: CategoryEditComponent},

    {path: '**', component: PrincipalComponent},
    {path: '', redirectTo: '/principal', pathMatch: 'full'},
];