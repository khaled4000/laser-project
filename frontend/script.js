let students = [];

async function fetchStudents() {
  showLoader(true);
  const response = await fetch('http://localhost:8080/students/getStudents');
  students = await response.json();
  showLoader(false);

  const list = document.getElementById('studentList');
  list.innerHTML = '';

  students.forEach((student, index) => {
    const li = document.createElement('li');
    li.innerHTML = `
      <strong>${student.name}</strong> (University: ${student.university})
      <button onclick="editStudent(${index})">✏️ Edit</button>
      <button onclick="deleteStudent(${index})">🗑️ Delete</button>
    `;
    list.appendChild(li);
  });
}

async function deleteStudent(index) {
  await fetch(`http://localhost:8080/students/deleteStudent/${index}`, {
    method: 'DELETE'
  });
  showNotification("Student deleted.", true);
  fetchStudents();
}

async function editStudent(index) {
  const student = students[index];
  const newName = prompt("Edit student name:", student.name);
  const newUniversity = prompt("Edit university:", student.university);

  if (newName && newUniversity) {
    await fetch(`http://localhost:8080/students/updateStudent/${index}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: newName.trim(), university: newUniversity.trim() })
    });
    showNotification("Student updated.", true);
    fetchStudents();
  }
}

async function getStudents() {
  const response = await fetch('http://localhost:8080/students/getStudents');
  return await response.json();
}

async function saveStudents(students) {
  await fetch('http://localhost:8080/students/save-all', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(students)
  });
}

async function addStudent() {
  const name = document.getElementById('studentName').value.trim();
  const university = document.getElementById('universityName').value.trim();

  if (!name || !university) {
    showNotification("Please enter both name and university.", false);
    return;
  }

  await fetch('http://localhost:8080/students/addStudent', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, university })
  });

  document.getElementById('studentName').value = '';
  document.getElementById('universityName').value = '';

  showNotification("Student added successfully! 🎉", true);
  fetchStudents();
}

function showNotification(message, success = true) {
  const notification = document.createElement('div');
  notification.textContent = message;
  notification.className = `notification ${success ? 'success' : 'error'}`;
  document.body.appendChild(notification);

  setTimeout(() => {
    notification.remove();
  }, 3000);
}

function showLoader(show) {
  const loader = document.getElementById('loader');
  loader.style.display = show ? 'block' : 'none';
}

async function exportStudents() {
  const students = await getStudents();
  const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(students, null, 2));
  const downloadAnchor = document.createElement('a');
  downloadAnchor.setAttribute("href", dataStr);
  downloadAnchor.setAttribute("download", "students.json");
  downloadAnchor.click();
}

window.onload = fetchStudents;
