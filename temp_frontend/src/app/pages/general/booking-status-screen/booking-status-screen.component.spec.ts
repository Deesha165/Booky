import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingStatusScreenComponent } from './booking-status-screen.component';

describe('BookingStatusScreenComponent', () => {
  let component: BookingStatusScreenComponent;
  let fixture: ComponentFixture<BookingStatusScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingStatusScreenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingStatusScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
