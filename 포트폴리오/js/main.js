/* ============================================================
   추지호 포트폴리오 — 메인 JavaScript
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {

  /* ──────────────────────────────────────────────
     1. NAVBAR — 스크롤 시 배경 추가 + Active link
  ─────────────────────────────────────────────── */
  const navbar   = document.getElementById('navbar');
  const navLinks = document.querySelectorAll('.nav-link');
  const sections = document.querySelectorAll('section[id]');

  const toggleNav = () => {
    navbar.classList.toggle('scrolled', window.scrollY > 60);
  };
  window.addEventListener('scroll', toggleNav, { passive: true });
  toggleNav();

  const highlightNavLink = () => {
    let current = '';
    sections.forEach(sec => {
      if (window.scrollY >= sec.offsetTop - 120) current = sec.id;
    });
    navLinks.forEach(l => {
      l.classList.toggle('active', l.getAttribute('href') === '#' + current);
    });
  };
  window.addEventListener('scroll', highlightNavLink, { passive: true });

  /* Mobile menu */
  const navToggle = document.getElementById('navToggle');
  const navMenu   = document.getElementById('navMenu');
  navToggle.addEventListener('click', () => navMenu.classList.toggle('open'));
  navLinks.forEach(l => l.addEventListener('click', () => navMenu.classList.remove('open')));


  /* ──────────────────────────────────────────────
     2. HERO PARTICLES
  ─────────────────────────────────────────────── */
  const particleContainer = document.getElementById('particles');
  const PARTICLE_COUNT = 18;
  const colors = ['#6366f1','#22d3ee','#f472b6','#4ade80','#fb923c'];

  for (let i = 0; i < PARTICLE_COUNT; i++) {
    const p = document.createElement('div');
    p.classList.add('particle');
    const size   = Math.random() * 4 + 2;
    const left   = Math.random() * 100;
    const delay  = Math.random() * 15;
    const dur    = Math.random() * 12 + 10;
    const color  = colors[Math.floor(Math.random() * colors.length)];
    p.style.cssText = `
      width:${size}px; height:${size}px;
      left:${left}%;
      background:${color};
      animation-duration:${dur}s;
      animation-delay:${delay}s;
      box-shadow: 0 0 ${size*2}px ${color};
    `;
    particleContainer.appendChild(p);
  }


  /* ──────────────────────────────────────────────
     3. HERO HEADLINE CARD 자동 로테이션 + 클릭
  ─────────────────────────────────────────────── */
  const hCards = document.querySelectorAll('.headline-card');
  const hDots  = document.querySelectorAll('.headline-dots .dot');
  let hlTimer  = null;
  let hlIdx    = 0;

  const setHL = (idx) => {
    hlIdx = idx;
    hCards.forEach((c, i) => c.classList.toggle('active', i === idx));
    hDots.forEach((d, i) => d.classList.toggle('active', i === idx));
  };

  const startHLTimer = () => {
    clearInterval(hlTimer);
    hlTimer = setInterval(() => setHL((hlIdx + 1) % hCards.length), 4000);
  };

  hCards.forEach((c, i) => c.addEventListener('click', () => { setHL(i); startHLTimer(); }));
  hDots.forEach((d, i) => d.addEventListener('click', () => { setHL(i); startHLTimer(); }));
  startHLTimer();


  /* ──────────────────────────────────────────────
     4. SCROLL REVEAL (Intersection Observer)
  ─────────────────────────────────────────────── */
  const addReveal = () => {
    const targets = [
      '.metric-card',
      '.portfolio-card',
      '.chart-card',
      '.erp-card',
      '.skill-card',
      '.lf-step',
      '.case-header',
      '.solo-banner',
      '.contact-card'
    ];
    targets.forEach(sel => {
      document.querySelectorAll(sel).forEach((el, i) => {
        el.classList.add('reveal');
        el.style.transitionDelay = `${i * 0.08}s`;
      });
    });
  };
  addReveal();

  const revealObserver = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.classList.add('visible');
        revealObserver.unobserve(e.target);
      }
    });
  }, { threshold: 0.1 });

  document.querySelectorAll('.reveal').forEach(el => revealObserver.observe(el));


  /* ──────────────────────────────────────────────
     5. COUNT-UP ANIMATION
  ─────────────────────────────────────────────── */
  const countUps = document.querySelectorAll('.count-up');

  const animateCount = (el) => {
    const target = parseInt(el.dataset.target, 10);
    const start  = parseInt(el.textContent, 10) || 0;
    const dur    = 1800;
    const step   = 16;
    const steps  = dur / step;
    const inc    = (target - start) / steps;
    let current  = start;

    const tick = () => {
      current += inc;
      if ((inc >= 0 && current >= target) || (inc < 0 && current <= target)) {
        el.textContent = target;
        return;
      }
      el.textContent = Math.round(current);
      requestAnimationFrame(tick);
    };
    requestAnimationFrame(tick);
  };

  const countObserver = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        animateCount(e.target);
        countObserver.unobserve(e.target);
      }
    });
  }, { threshold: 0.5 });

  countUps.forEach(el => countObserver.observe(el));


  /* ──────────────────────────────────────────────
     6. SKILL BAR ANIMATION
  ─────────────────────────────────────────────── */
  const skillBars = document.querySelectorAll('.skill-fill');
  const barObserver = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.style.width = e.target.getAttribute('style').match(/width:(\S+)/)?.[1] || '70%';
        barObserver.unobserve(e.target);
      }
    });
  }, { threshold: 0.3 });

  skillBars.forEach(bar => {
    const target = bar.style.width;
    bar.style.width = '0';
    bar.dataset.target = target;
    barObserver.observe(bar);
  });

  // Fix: restore target widths on tab switch
  const skillTabs = document.querySelectorAll('.skill-tab');
  skillTabs.forEach(tab => {
    tab.addEventListener('click', () => {
      const id = tab.dataset.tab;
      document.querySelectorAll('.skill-panel').forEach(p => p.classList.remove('active'));
      document.querySelectorAll('.skill-tab').forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
      const panel = document.getElementById(`tab-${id}`);
      if (panel) {
        panel.classList.add('active');
        // animate skill bars in new panel
        panel.querySelectorAll('.skill-fill').forEach(bar => {
          setTimeout(() => {
            bar.style.width = bar.dataset.target || '70%';
          }, 80);
        });
      }
    });
  });


  /* ──────────────────────────────────────────────
     7. CHARTS (Chart.js)
  ─────────────────────────────────────────────── */
  const chartDefaults = {
    font: { family: "'Inter', sans-serif" },
    color: '#64748b',
  };
  Chart.defaults.font.family = chartDefaults.font.family;
  Chart.defaults.color       = chartDefaults.color;

  // ── 7-1. 전자결재 처리 성능 개선 Bar Chart
  const perfCtx = document.getElementById('performanceChart')?.getContext('2d');
  if (perfCtx) {
    new Chart(perfCtx, {
      type: 'bar',
      data: {
        labels: ['건별 INSERT\n(최적화 전)', 'Bulk INSERT\n(최적화 후)', 'STRAIGHT_JOIN\n제거 후', '인덱스\n추가 후'],
        datasets: [{
          label: '처리 성능 (상대값)',
          data: [40, 95, 75, 100],
          backgroundColor: [
            'rgba(248,113,113,0.6)',
            'rgba(99,102,241,0.7)',
            'rgba(34,211,238,0.6)',
            'rgba(74,222,128,0.7)',
          ],
          borderColor: [
            '#f87171', '#6366f1', '#22d3ee', '#4ade80'
          ],
          borderWidth: 1,
          borderRadius: 8,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: ctx => ` 성능 지수: ${ctx.parsed.y}`
            }
          }
        },
        scales: {
          x: {
            grid: { color: 'rgba(255,255,255,0.04)' },
            ticks: { color: '#64748b', font: { size: 10 } }
          },
          y: {
            beginAtZero: true,
            max: 110,
            grid: { color: 'rgba(255,255,255,0.04)' },
            ticks: {
              color: '#64748b',
              callback: v => v + '%'
            }
          }
        }
      }
    });
  }

  // ── 7-2. 연간 장애 재발 현황 Line Chart
  const incidentCtx = document.getElementById('incidentChart')?.getContext('2d');
  if (incidentCtx) {
    new Chart(incidentCtx, {
      type: 'line',
      data: {
        labels: ['Y-2', 'Y-1', 'SocketException\n해결 후', '리팩토링\n완료 후', 'Current'],
        datasets: [{
          label: '재발 장애 건수',
          data: [12, 8, 3, 1, 0],
          borderColor: '#6366f1',
          backgroundColor: 'rgba(99,102,241,0.12)',
          pointBackgroundColor: ['#f87171','#fb923c','#facc15','#60a5fa','#4ade80'],
          pointRadius: 7,
          pointHoverRadius: 9,
          tension: 0.4,
          fill: true,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: ctx => ` 재발 장애: ${ctx.parsed.y}건`
            }
          }
        },
        scales: {
          x: {
            grid: { color: 'rgba(255,255,255,0.04)' },
            ticks: { color: '#64748b', font: { size: 10 } }
          },
          y: {
            beginAtZero: true,
            grid: { color: 'rgba(255,255,255,0.04)' },
            ticks: { color: '#64748b', stepSize: 2 }
          }
        }
      }
    });
  }

  // ── 7-3. 이슈 유형별 Doughnut
  const issueCtx = document.getElementById('issueChart')?.getContext('2d');
  if (issueCtx) {
    new Chart(issueCtx, {
      type: 'doughnut',
      data: {
        labels: ['전자결재', '메일', '일정/캘린더', '게시판', '자원관리', '기타'],
        datasets: [{
          data: [35, 22, 15, 13, 10, 5],
          backgroundColor: [
            'rgba(99,102,241,0.8)',
            'rgba(34,211,238,0.8)',
            'rgba(244,114,182,0.8)',
            'rgba(74,222,128,0.8)',
            'rgba(251,146,60,0.8)',
            'rgba(100,116,139,0.6)',
          ],
          borderColor: 'transparent',
          hoverOffset: 8,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '60%',
        plugins: {
          legend: {
            position: 'bottom',
            labels: {
              padding: 14,
              font: { size: 11 },
              color: '#94a3b8',
              boxWidth: 14,
              borderRadius: 4,
            }
          },
          tooltip: {
            callbacks: {
              label: ctx => ` ${ctx.label}: ${ctx.parsed}%`
            }
          }
        }
      }
    });
  }


  /* ──────────────────────────────────────────────
     8. CASE STUDY TABS
  ─────────────────────────────────────────────── */
  const caseTabs   = document.querySelectorAll('.case-tab');
  const casePanels = document.querySelectorAll('.case-panel');

  caseTabs.forEach(tab => {
    tab.addEventListener('click', () => {
      const caseId = tab.dataset.case;
      caseTabs.forEach(t => t.classList.remove('active'));
      casePanels.forEach(p => p.classList.remove('active'));
      tab.classList.add('active');
      const panel = document.getElementById(`case-${caseId}`);
      if (panel) panel.classList.add('active');
    });
  });


  /* ──────────────────────────────────────────────
     9. CODE COPY BUTTON
  ─────────────────────────────────────────────── */
  window.copyCode = (btn) => {
    const pre  = btn.closest('.code-snippet').querySelector('pre');
    const text = pre.textContent || '';
    navigator.clipboard.writeText(text).then(() => {
      btn.innerHTML = '<i class="fas fa-check"></i> 복사됨';
      btn.classList.add('copied');
      setTimeout(() => {
        btn.innerHTML = '<i class="fas fa-copy"></i>';
        btn.classList.remove('copied');
      }, 2000);
    }).catch(() => {
      // fallback
      const ta = document.createElement('textarea');
      ta.value = text;
      document.body.appendChild(ta);
      ta.select();
      document.execCommand('copy');
      document.body.removeChild(ta);
      btn.innerHTML = '<i class="fas fa-check"></i>';
      btn.classList.add('copied');
      setTimeout(() => {
        btn.innerHTML = '<i class="fas fa-copy"></i>';
        btn.classList.remove('copied');
      }, 2000);
    });
  };


  /* ──────────────────────────────────────────────
     10. SCROLL TO TOP
  ─────────────────────────────────────────────── */
  const scrollTopBtn = document.getElementById('scrollTop');
  window.addEventListener('scroll', () => {
    scrollTopBtn.classList.toggle('visible', window.scrollY > 400);
  }, { passive: true });
  scrollTopBtn.addEventListener('click', () => window.scrollTo({ top: 0, behavior: 'smooth' }));


  /* ──────────────────────────────────────────────
     11. PERFORMANCE BAR ANIMATION (ERP section)
  ─────────────────────────────────────────────── */
  const perfBars = document.querySelectorAll('.pi-bar');
  const perfBarObs = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        if (e.target.classList.contains('before-bar')) e.target.style.width = '150px';
        if (e.target.classList.contains('after-bar'))  e.target.style.width = '300px';
        perfBarObs.unobserve(e.target);
      }
    });
  }, { threshold: 0.5 });
  perfBars.forEach(b => perfBarObs.observe(b));


  /* ──────────────────────────────────────────────
     12. PORTFOLIO CARD — hover 3D tilt
  ─────────────────────────────────────────────── */
  document.querySelectorAll('.portfolio-card, .metric-card').forEach(card => {
    card.addEventListener('mousemove', (e) => {
      const rect   = card.getBoundingClientRect();
      const cx     = rect.left + rect.width  / 2;
      const cy     = rect.top  + rect.height / 2;
      const dx     = (e.clientX - cx) / (rect.width  / 2);
      const dy     = (e.clientY - cy) / (rect.height / 2);
      const tiltX  = -dy * 4;
      const tiltY  =  dx * 4;
      card.style.transform = `perspective(900px) rotateX(${tiltX}deg) rotateY(${tiltY}deg) translateY(-4px)`;
    });
    card.addEventListener('mouseleave', () => {
      card.style.transform = '';
    });
  });


  /* ──────────────────────────────────────────────
     13. SMOOTH ANCHOR SCROLL
  ─────────────────────────────────────────────── */
  document.querySelectorAll('a[href^="#"]').forEach(a => {
    a.addEventListener('click', (e) => {
      const id  = a.getAttribute('href').slice(1);
      const el  = document.getElementById(id);
      if (el) {
        e.preventDefault();
        const top = el.offsetTop - 70;
        window.scrollTo({ top, behavior: 'smooth' });
      }
    });
  });


  /* ──────────────────────────────────────────────
     14. COMMIT FEED — 필터 & 더보기
  ─────────────────────────────────────────────── */
  const cfFilters = document.querySelectorAll('.cf-filter');
  const commitItems = document.querySelectorAll('.commit-item');
  const commitFeed  = document.getElementById('commitFeed');
  const cfMoreBtn   = document.getElementById('cfMoreBtn');

  // 필터 기능
  cfFilters.forEach(btn => {
    btn.addEventListener('click', () => {
      cfFilters.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      const filter = btn.dataset.filter;
      commitItems.forEach(item => {
        if (filter === 'all' || item.dataset.type === filter) {
          item.style.display = '';
        } else {
          item.style.display = 'none';
        }
      });
      // 필터 변경 시 더보기 상태 초기화
      if (commitFeed) {
        commitFeed.classList.remove('expanded');
        if (cfMoreBtn) {
          cfMoreBtn.classList.remove('open');
          cfMoreBtn.innerHTML = '<i class="fas fa-chevron-down"></i> 더 보기';
        }
      }
    });
  });

  // 더보기 토글
  if (cfMoreBtn && commitFeed) {
    cfMoreBtn.addEventListener('click', () => {
      const isOpen = commitFeed.classList.toggle('expanded');
      cfMoreBtn.classList.toggle('open', isOpen);
      cfMoreBtn.innerHTML = isOpen
        ? '<i class="fas fa-chevron-up"></i> 접기'
        : '<i class="fas fa-chevron-down"></i> 더 보기';
    });
  }


  /* ──────────────────────────────────────────────
     15. IST-FILL 바 애니메이션 (Commit Log 섹션)
  ─────────────────────────────────────────────── */
  const istFills = document.querySelectorAll('.ist-fill');
  const istObs = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        const target = getComputedStyle(e.target).getPropertyValue('--w').trim();
        e.target.style.width = target;
        istObs.unobserve(e.target);
      }
    });
  }, { threshold: 0.3 });
  istFills.forEach(f => { f.style.width = '0'; istObs.observe(f); });


}); // DOMContentLoaded
