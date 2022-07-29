


const subjects = {
    "Art": ["Ceramics", "Drawing and painting"],
    "Math": ["Integrated 1", "Integrated 2", "Integrated 3", "AP Calculus AB", "AP Calculus BC", "AP Statistics"],
    "English": ["English 1", "English 2", "Honors English 1", "Honors English 2"],
    "Foreign Language": ["Spanish 1", "Spanish 2", "Spanish 3", "Spanish 4", "AP Spanish Language", "AP Spanish Literature", "French 1", "French 2", "French 3", "French 4", "AP French", "German 1", "German 2", "German 3", "German 4", "AP German", "Latin 1", "Latin 2", "Latin 3", "Latin 4", "AP Latin", "Japanese 1", "Japanese 2", "Japanese 3", "Japanese 4", "AP Japanese", "Chinese 1", "Chinese 2", "Chinese 3", "Chinese 4", "AP Chinese"],
    "History": ["AP US", "AP European", "AP World", "US", "European", "World"],
    "Life Science": ["Biology 1", "Biology 2", "Honors Biology", "AP Biology", "AP Environmental"],
    "Physical Science": ["Chemistry 1", "Chemistry 2", "Honors Chemistry", "AP Chemistry", "Physics 1", "Physics 2", "Honors Physics", "Physics 1: Algebra Based", "AP Physics 2: Algebra Based", "AP Physics C: Mechanics", "AP Physics C: Electricity and Magnetism", "AP Environmental"],
    "Health": ["Health"],
    "PE": ["PE"],
    "Elective": ["Elective"]
};

let requiredSection = `
<hr>

<div>
    <select id="drop" name="requiredDropdownSubjects">

    </select>

    <label for="requiredClassesLength">Length</label>
    <input type="text" name="requiredClassesLength" id="required-classes__input--length">
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
        <select id="dropClasses" name="wantedDropdownClasses">
        
        </select>


        </ul>
    </div>
    <label for="wantedClassesLength">Length</label>
    <input type="text" name="wantedClassesLength" id="wanted-classes__input--length">
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
    console.log("CREATELIST");
    createCourseList(wantedClasses,this.value);
});
addSubjects(wantedSubjects, "wanted");
let numWantedClasses = 1;


function addSubjects(element, IDtype) {
    for (let key of keys) {

        let option = document.createElement("option");

        option.id = IDtype + "-classes__option--" + key;

        console.log("PRELIST");

        // 
        option.value = key;
        option.innerText = key;

        element.appendChild(option);


    }
}

//Skills and Interest
const skillsInterestUl = document.querySelector("#skills-interest");
const skillsOrInterest = ["Skills", "Interest"];
for (let key of keys) {


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
        skillsInterestUl.appendChild(label);

        skillsInterestUl.appendChild(li);
    }

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