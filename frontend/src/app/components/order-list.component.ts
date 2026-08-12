import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Order, OrderStatus } from '../order.model';
import { OrderRowComponent } from './order-row.component';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, OrderRowComponent],
  template: `
    <div class="list">
      <div class="head">
        <span>Reference</span>
        <span>Customer</span>
        <span class="right">Amount</span>
        <span class="right">Status</span>
      </div>
      <app-order-row
        *ngFor="let order of orders; trackBy: trackById"
        [order]="order"
        (statusChanged)="statusChanged.emit($event)"
      ></app-order-row>
      <p class="empty" *ngIf="orders.length === 0">No orders match this filter.</p>
    </div>
  `,
  styles: [
    `
      .list {
        background: var(--surface);
        border: 1px solid var(--border);
        border-radius: 10px;
        overflow: hidden;
      }
      .head {
        display: grid;
        grid-template-columns: 120px 1fr 120px 260px;
        gap: 16px;
        padding: 10px 16px;
        border-bottom: 1px solid var(--border);
        background: var(--bg-alt);
        color: var(--muted);
        font-size: 11px;
        text-transform: uppercase;
        letter-spacing: 0.06em;
      }
      .head .right {
        text-align: right;
      }
      .empty {
        margin: 0;
        padding: 24px 16px;
        color: var(--muted);
        text-align: center;
      }
    `
  ]
})
export class OrderListComponent {
  @Input() orders: Order[] = [];
  @Output() statusChanged = new EventEmitter<{ id: number; status: OrderStatus }>();

  trackById(_index: number, order: Order): number {
    return order.id;
  }
}
