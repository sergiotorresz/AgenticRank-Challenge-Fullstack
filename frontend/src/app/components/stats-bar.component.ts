import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Stats } from '../order.model';

@Component({
  selector: 'app-stats-bar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="stats">
      <div class="stat">
        <span class="label">Revenue today</span>
        <span class="value accent">{{ revenue }}</span>
      </div>
      <div class="divider"></div>
      <div class="stat">
        <span class="label">Active orders</span>
        <span class="value">{{ stats?.activeCount ?? 0 }}</span>
      </div>
    </div>
  `,
  styles: [
    `
      .stats {
        display: flex;
        align-items: center;
        gap: 20px;
        background: var(--surface);
        border: 1px solid var(--border);
        border-radius: 10px;
        padding: 12px 20px;
      }
      .stat {
        display: flex;
        flex-direction: column;
        gap: 2px;
      }
      .label {
        color: var(--muted);
        font-size: 11px;
        text-transform: uppercase;
        letter-spacing: 0.06em;
      }
      .value {
        font-size: 20px;
        font-weight: 600;
        font-variant-numeric: tabular-nums;
      }
      .value.accent {
        color: var(--accent);
      }
      .divider {
        width: 1px;
        align-self: stretch;
        background: var(--border);
      }
    `
  ]
})
export class StatsBarComponent {
  @Input() stats: Stats | null = null;

  get revenue(): string {
    const cents = this.stats?.revenueTodayCents ?? 0;
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(cents / 100);
  }
}
