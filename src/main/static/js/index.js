function sortTable(n) {
    let table = document.getElementById('myTable');
   let tableBody = table.getElementsByTagName('tbody')[0];
   let sortDirection = false;
   sortDirection = !sortDirection;
    let rows = tableBody.rows;
    let sortArray = [];

    for (let i = 0; i < rows.length; i++) {
        let cell = rows[i].getElementsByTagName('td')[n];

        sortArray.push({
           value: cell.innerHTML,
           row : rows[i]
        })
    }

        sortColumnOfNumber(sortDirection, sortArray);

      function sortColumnOfNumber(sort, sortArray) {
        sortArray.sort((a1, a2) => {
            return sort ? a2.value - a1.value : a1.value - a2.value;
        }
        )
        reloadTable(sortArray)
      }

      function reloadTable(sortArray) {
        tableBody.innerHTML = "";

        for (let i=0; i < sortArray.length; i++) {
             tableBody.appendChild(sortArray[i].row)
        }

      }
      }

