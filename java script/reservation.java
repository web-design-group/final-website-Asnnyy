            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('resForm');
                const reserveBtn = document.getElementById('reserveBtn');
                const dateInput = document.getElementById('dateInput');
                const timeInput = document.getElementById('timeInput');
                const nameInput = document.getElementById('nameInput');
                const guestsInput = document.getElementById('guestsInput');
                const phoneInput = document.getElementById('phoneInput');
                
                const modalDate = document.getElementById('modalDate');
                const modalTime = document.getElementById('modalTime');
                const modalImage = document.getElementById('modalImage');
                
                const modal = new bootstrap.Modal(document.getElementById('resModal'));

                function formatDate(dateString) {
                    const date = new Date(dateString);
                    const day = date.getDate().toString().padStart(2, '0');
                    const month = (date.getMonth() + 1).toString().padStart(2, '0');
                    const year = date.getFullYear();
                    return `${day}.${month}.${year}`;
                }

                function validateName(name) {
                    const regex = /^[A-Za-zА-Яа-яЁё\s\-]+$/;
                    return regex.test(name);
                }

                function validatePhone(phone) {
                    const regex = /^[\+\d\s\-\(\)]+$/;
                    return regex.test(phone) && phone.replace(/\D/g, '').length >= 10;
                }


                function checkForm() {
                    const inputs = [dateInput, timeInput, guestsInput, nameInput, phoneInput];
                    let allValid = true;
                    
                    inputs.forEach(input => {
                        if (!input.value.trim()) {
                            allValid = false;
                        }
                    });
                    
                    if (nameInput.value.trim() && !validateName(nameInput.value.trim())) {
                        allValid = false;
                        errorMessage = 'Имя может содержать только буквы, пробелы и дефисы';
                        nameInput.style.borderColor = 'red';
                    } else {
                        nameInput.style.borderColor = '#918474';
                    }

                    if (phoneInput.value.trim() && !validatePhone(phoneInput.value.trim())) {
                        allValid = false;
                        errorMessage = 'Введите корректный номер телефона (минимум 10 цифр)';
                    }

                    if (dateInput.value) {
                        const selectedDate = new Date(dateInput.value);
                        const today = new Date();
                        today.setHours(0, 0, 0, 0);
            
                        if (selectedDate < today) {
                            allValid = false;
                            errorMessage = 'Нельзя выбрать прошедшую дату';
                            dateInput.style.borderColor = 'red';
                        } else {
                            dateInput.style.borderColor = '#918474';
                        }
                    }

                    reserveBtn.disabled = !allValid;
                    
                    if (allValid) {
                        reserveBtn.setAttribute('data-bs-toggle', 'modal');
                        reserveBtn.setAttribute('data-bs-target', '#resModal');
                    } else {
                        reserveBtn.removeAttribute('data-bs-toggle');
                        reserveBtn.removeAttribute('data-bs-target');
                    }
                    
                    return allValid;
                }

                function fillModal() {
                    const date = dateInput.value;
                    const time = timeInput.value;
                    
                    if (date && time) {
                        modalDate.textContent = formatDate(date);
                        modalTime.textContent = time;
                        modalImage.src = 'img/modal.png';
                        return true;
                    }
                    return false;
                }

                reserveBtn.addEventListener('click', function(e) {
                    if (!checkForm()) {
                        e.preventDefault();
                        alert('Заполните все поля');
                        return;
                    }
                    
                    fillModal();
                });

                const today = new Date().toISOString().split('T')[0];
                dateInput.min = today;
                
                
                form.addEventListener('input', checkForm);
                form.addEventListener('change', checkForm);

                checkForm();
                
                document.getElementById('resModal').addEventListener('hidden.bs.modal', function() {
                    form.reset();
                    dateInput.value = new Date().toISOString().split('T')[0];
                    checkForm();
                });
            });