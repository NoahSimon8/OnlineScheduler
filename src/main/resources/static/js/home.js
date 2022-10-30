


const subjects = {
    "Select a Subject": ["1","2","3","4","5"],
    "Art": ["1","2","3","4","5"],
    "Math": ["1","2","3","4","5"],
    "English": ["1","2","3","4","5"],
    "Foreign_Language": ["1","2","3","4","5"],
    "History": ["1","2","3","4","5"],
    "Life_Science": ["1","2","3","4","5"],
    "Physical_Science": ["1","2","3","4","5"],
    "PE": ["1"],
    "Elective": ["1","2","3","4","5"]
};

let requiredSection = `
<hr>

<div>
    <select id="drop" name="requiredDropdownSubjects">

    </select>

    <label for="requiredClassesLength"># of terms</label>
    <input type="text" name="requiredClassesLengthStr" id="required-classes__input--length">
</div>
`;

let wantedSection = `
<hr>
<div>
    <div class="dropdown">
        <select id="dropSubjects" name="wantedDropdownSubjects">
        
        </select>


    </div>
    <!-- dropdown class -->
    <div class="dropdown">
        <label for="wantedDropdownClassesStr"> Difficulty</label>

        <select id="dropClasses" name="wantedDropdownClasses">
        
        </select>


        </ul>
    </div>
    <label for="wantedClassesLength"># of terms</label>
    <input type="text" name="wantedClassesLengthStr" id="wanted-classes__input--length">
</div>`;

const keys = Object.keys(subjects);


// Required Classes
const requiredTitle = document.getElementById("required-classes__button--dropdown-menu");
const requiredSubjects = document.getElementById("required-classes__select--subjects");
addSubjects(requiredSubjects, "required");
let numRequiredClasses = 1;


// Wanted Classes

//Subject

const wantedSubjects = document.getElementById("wanted-classes__select--subjects");
const wantedClasses = document.getElementById("wanted-classes__select--classes");
wantedSubjects.addEventListener("change", function () {
    createCourseList(wantedClasses,this.value);
});
addSubjects(wantedSubjects, "wanted");
let numWantedClasses = 1;


function addSubjects(element, IDtype) {
    for (let key of keys) {

        let option = document.createElement("option");

        option.id = IDtype + "-classes__option--" + key;


        // 
        option.value = key;
        option.innerText = key;

        element.appendChild(option);


    }
}

let skillsInterestUl = document.querySelector("#skills-interest");
const skillsOrInterest = ["Skills", "Interest"];
let count = 0;
for (let key of keys) {
    if (key=="Select a Subject"){
        continue;
    }

    if (count>=5){
        skillsInterestUl=document.getElementById("skills-interest2")
    }
    console.log(skillsInterestUl)

    let header = document.createElement("h5")
    header.innerText= key
    skillsInterestUl.appendChild(header)

    for (let option of skillsOrInterest) {
        let li = document.createElement("li");
        let label = document.createElement("label");
        label.innerText = key + " " + option;
        for (let i = 0; i < 5; i++) {

            let radio = document.createElement("input");
            radio.type = "radio";
            subj = key.replace(" ", "_");   

            radio.name = "radio" + subj + option;
            radio.value = i;
            li.appendChild(radio);
        }
        label.classList.add("pl")
        li.classList.add("pl")

        skillsInterestUl.appendChild(label);

        skillsInterestUl.appendChild(li);
    }

    count++;

}




// slider functinality 
function rangeSlider(value, id) {
    document.getElementById(id).innerHTML = value;
}


function createCourseList(element,keySubject) {
    //Class

    element.innerHTML = "";
    element.innerText = "Pick a class";
    for (let course of subjects[keySubject]) {

        let option = document.createElement("option");

        option.id = "wanted-classes__option--" + course;

        option.value = course;
        option.innerText = course;

        console.log("APPEND");
        element.appendChild(option);
    }


}





function addRequiredClass() {
    const requiredSection_modal = document.getElementById("required-classes__modal-body");
    requiredSection_modal.insertAdjacentHTML("beforeend", requiredSection);
    select = document.getElementById("drop");
    select.id = "required-classes__select--subjects" + numRequiredClasses;

    addSubjects(select, "required");
    numRequiredClasses++;
}




function addWantedClass() {
    const wantedSection_modal = document.getElementById("wanted-classes__modal-body");
    wantedSection_modal.insertAdjacentHTML("beforeend", wantedSection);
    let selectSubjects = document.getElementById("dropSubjects");
    selectSubjects.id = "wanted-classes__select--subjects" + numWantedClasses;
    let selectClasses = document.getElementById("dropClasses");
    selectClasses.id = "wanted-classes__select--classes" + numWantedClasses;

    console.log("update2")
    selectSubjects.addEventListener("change", function () {
        console.log("2nd Add Classes");
        createCourseList(selectClasses,this.value);
    });
    console.log(selectSubjects);

    addSubjects(selectSubjects, "wanted");

    numWantedClasses++
}