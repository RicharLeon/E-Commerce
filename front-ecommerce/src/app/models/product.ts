// product.interface.ts
export interface Product {
    id?: number;
    name: string;
    idCategory: number;
    nameCategory?: string;      
    price: number;
    stock: number;
    status: boolean; 
    description?: string;       // Descripción del producto
    photo?: string;             // URL de la imagen (opcional)
    createdAt?: Date;          // Fecha en formato ISO 8601
    updatedAt?: Date;          // Fecha en formato ISO 8601
    isEditing?: boolean;        // Para modo edición
    originalPrice?: number;     // Para guardar precio original durante edición

  }

  export interface PagedResponse {
    items: Product[];
    content: Product[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    first: boolean;
    last: boolean;
  }