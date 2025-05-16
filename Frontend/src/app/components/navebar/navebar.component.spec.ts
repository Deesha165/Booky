import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerNavebareComponent } from './navebar.component';

describe('CustomerNavebareComponent', () => {
  let component: CustomerNavebareComponent;
  let fixture: ComponentFixture<CustomerNavebareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerNavebareComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerNavebareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
