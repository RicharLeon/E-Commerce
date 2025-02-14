import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router'



@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  products = [
    {
      id: 1,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 1,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 2,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 3,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 4,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 5,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }, {
      id: 6,
      image: 'https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp',
      title: 'Shoes!',
      description: 'If a dog chews shoes whose shoes does he choose?'
    }


  ];


  constructor(private router: Router) { }

  onProductClick(id: number) {
    this.router.navigate(['/product-overviews']);
    console.log(id);
  }

}
