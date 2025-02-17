import { TestBed } from '@angular/core/testing';

import { CarritoItemsService } from './carrito-items.service';

describe('CarritoItemsService', () => {
  let service: CarritoItemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarritoItemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
