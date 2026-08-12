import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Order, OrderStatus, ORDER_STATUSES } from '../order.model';

@Component({
  selector: 'app-order-row',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="row">
      <span class="ref">{{ order.reference }}</span>
      <span class="customer">{{ order.customer }}</span>
      <span class="amount">{{ amount }}</span>
      <span class="status">
        <span class="badge" [style.color]="color" [style.borderColor]="color">{{ order.status }}</span>
        <select [value]="order.status" (change)="onChange($any($event.target).value)">
          <option *ngFor="let s of statuses" [value]="s">{{ s }}</option>
        </select>
      </span>
    </div>
  `,
  styles: [
    `
      .row {
        display: grid;
        grid-template-columns: 120px 1fr 120px 260px;
        align-items: center;
        gap: 16px;
        padding: 12px 16px;
        border-bottom: 1px solid var(--border);
      }
      .row:last-child {
        border-bottom: none;
      }
      .ref {
        font-family: ui-monospace, "SF Mono", Menlo, Consolas, monospace;
        color: var(--fg);
      }
      .customer {
        color: var(--fg);
      }
      .amount {
        text-align: right;
        font-variant-numeric: tabular-nums;
        color: var(--fg);
      }
      .status {
        display: flex;
        align-items: center;
        gap: 12px;
        justify-content: flex-end;
      }
      .badge {
        display: inline-block;
        padding: 2px 10px;
        border: 1px solid;
        border-radius: 999px;
        font-size: 11px;
        font-weight: 600;
        letter-spacing: 0.04em;
        white-space: nowrap;
      }
      select {
        background: var(--bg-alt);
        color: var(--fg);
        border: 1px solid var(--border);
        border-radius: 6px;
        padding: 6px 8px;
        min-width: 120px;
      }
      select:focus {
        outline: none;
        border-color: var(--accent);
      }
    `
  ]
})
export class OrderRowComponent {
  @Input() order!: Order;
  @Output() statusChanged = new EventEmitter<{ id: number; status: OrderStatus }>();

  readonly statuses = ORDER_STATUSES;

  private readonly colors: Record<OrderStatus, string> = {
    RECEIVED: '#8b8f98',
    PREPARING: '#FFB84D',
    READY: '#38bdf8',
    DELIVERED: '#00e5a0',
    CANCELLED: '#ef4444'
  };

  get color(): string {
    return this.colors[this.order.status];
  }

  get amount(): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(this.order.amountCents / 100);
  }

  onChange(value: string): void {
    this.statusChanged.emit({ id: this.order.id, status: value as OrderStatus });
  }
}
