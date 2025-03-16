const isLeapYear = (year) => {
    return (
        (year % 4 === 0 && year % 100 !== 0 && year % 400 !== 0) ||
        (year % 100 === 0 && year % 400 === 0)
    );
};

const getFebDays = (year) => {
    return isLeapYear(year) ? 29 : 28;
};

let calendar = document.querySelector('.calendar');
const month_names = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
];

let month_picker = document.querySelector('#month-picker');
const dayTextFormate = document.querySelector('.day-text-formate');
const timeFormate = document.querySelector('.time-formate');
const dateFormate = document.querySelector('.date-formate');

month_picker.onclick = () => {
    month_list.classList.remove('hideonce');
    month_list.classList.remove('hide');
    month_list.classList.add('show');
    dayTextFormate.classList.remove('showtime');
    dayTextFormate.classList.add('hidetime');
    timeFormate.classList.remove('showtime');
    timeFormate.classList.add('hideTime');
    dateFormate.classList.remove('showtime');
    dateFormate.classList.add('hideTime');
};

const generateCalendar = (month, year) => {
    let calendar_days = document.querySelector('.calendar-days');
    calendar_days.innerHTML = '';
    let calendar_header_year = document.querySelector('#year');
    let days_of_month = [31, getFebDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    let currentDate = new Date();

    month_picker.innerHTML = month_names[month];
    calendar_header_year.innerHTML = year;

    let first_day = new Date(year, month);

    for (let i = 0; i <= days_of_month[month] + first_day.getDay() - 1; i++) {
        let day = document.createElement('div');
        if (i >= first_day.getDay()) {
            const dayNumber = i - first_day.getDay() + 1;
            day.innerHTML = dayNumber;

            day.addEventListener('click', () => {
                const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(dayNumber).padStart(2, '0')}`;
                console.log("Date clicked:", formattedDate); // 调试信息
                const event = new CustomEvent('dateClick', { detail: formattedDate });
                document.dispatchEvent(event);
            });

            if (dayNumber === currentDate.getDate() &&
                year === currentDate.getFullYear() &&
                month === currentDate.getMonth()
            ) {
                day.classList.add('current-date');
            }

            // 检查是否有待办事项
            const dateKey = `${year}-${String(month + 1).padStart(2, '0')}-${String(dayNumber).padStart(2, '0')}`;
            const todos = JSON.parse(localStorage.getItem('calendarTodos')) || {};
            if (todos[dateKey]) {
                const hasUncompleted = todos[dateKey].some(todo => !todo.completed);
                const allCompleted = todos[dateKey].every(todo => todo.completed);

                if (hasUncompleted) {
                    day.classList.add('has-uncompleted-todo');
                } else if (allCompleted) {
                    day.classList.add('has-completed-todo');
                }
            }
        }
        calendar_days.appendChild(day);
    }
};

let month_list = calendar.querySelector('.month-list');
month_names.forEach((e, index) => {
    let month = document.createElement('div');
    month.innerHTML = `<div>${e}</div>`;
    month_list.append(month);
    month.onclick = () => {
        currentMonth.value = index;
        generateCalendar(currentMonth.value, currentYear.value);
        month_list.classList.replace('show', 'hide');
        dayTextFormate.classList.remove('hideTime');
        dayTextFormate.classList.add('showtime');
        timeFormate.classList.remove('hideTime');
        timeFormate.classList.add('showtime');
        dateFormate.classList.remove('hideTime');
        dateFormate.classList.add('showtime');
    };
});

(function () {
    month_list.classList.add('hideonce');
})();

document.querySelector('#pre-year').onclick = () => {
    --currentYear.value;
    generateCalendar(currentMonth.value, currentYear.value);
};
document.querySelector('#next-year').onclick = () => {
    ++currentYear.value;
    generateCalendar(currentMonth.value, currentYear.value);
};

let currentDate = new Date();
let currentMonth = { value: currentDate.getMonth() };
let currentYear = { value: currentDate.getFullYear() };
generateCalendar(currentMonth.value, currentYear.value);

const todayShowTime = document.querySelector('.time-formate');
const todayShowDate = document.querySelector('.date-formate');

const currshowDate = new Date();
const showCurrentDateOption = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
const currentDateFormate = new Intl.DateTimeFormat('en-US', showCurrentDateOption).format(currshowDate);
todayShowDate.textContent = currentDateFormate;

setInterval(() => {
    const timer = new Date();
    const option = { hour: 'numeric', minute: 'numeric', second: 'numeric' };
    const formateTimer = new Intl.DateTimeFormat('en-us', option).format(timer);
    todayShowTime.textContent = formateTimer;
}, 1000);

// 监听刷新日历的事件
document.addEventListener('refreshCalendar', () => {
    generateCalendar(currentMonth.value, currentYear.value);
});

// 监听关闭抽屉的事件
document.addEventListener('closeDrawer', () => {
    const selectedDate = vm.selectedDate;
    const todos = JSON.parse(localStorage.getItem('calendarTodos')) || {};

    // 检查当天是否有待办事项
    if (!todos[selectedDate] || todos[selectedDate].length === 0) {
        // 如果没有待办事项，移除该日期的标记
        const dateKey = selectedDate;
        const calendarDays = document.querySelector('.calendar-days');
        const dayElements = calendarDays.querySelectorAll('div');
        
        dayElements.forEach(day => {
            if (day.textContent === new Date(dateKey).getDate().toString()) {
                day.classList.remove('has-uncompleted-todo', 'has-completed-todo');
            }
        });
    }
});

// calendar.js 修改以下部分
document.addEventListener('clearDateMark', (e) => {
    const dateKey = e.detail;
    const [year, month, day] = dateKey.split('-').map(Number);
    
    // 获取当前日历的年份和月份
    const currentCalendarYear = currentYear.value;
    const currentCalendarMonth = currentMonth.value;

    // 只有当前展示的月份年份匹配时才操作DOM
    if (year === currentCalendarYear && (month - 1) === currentCalendarMonth) {
        const calendarDays = document.querySelector('.calendar-days');
        const dayElements = calendarDays.querySelectorAll('div');
        
        dayElements.forEach(dayElement => {
            if (dayElement.textContent === day.toString()) {
                dayElement.classList.remove('has-uncompleted-todo', 'has-completed-todo');
            }
        });
    }
});