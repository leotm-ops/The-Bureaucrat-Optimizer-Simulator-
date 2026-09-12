# The-Bureaucrat-Optimizer-Simulator
"A Java-based constraint satisfaction and document packaging simulator designed to optimize university administrative workflows. 
Developed for a Software Development course challenge at UFRGS."

## Authors
* **Lara Moreira**
* **Leonardo Maraschin**
* **Ana Laura Führ**

---

## Key Features & Architecture

### Core Simulation Loop
* **Periodic Execution (`trabalhar()`):** Invoked every 50ms by the simulation runner to evaluate document stacks and table slots (`Mesa`).

### Regulatory Business Rules
* **Page Limit Enforcement:** Restricts processes to a maximum of 250 pages to prevent package ruptures and bureaucrat stress penalties.
* **Academic vs. Administrative Isolation:** Segregates administrative and academic documents (with exception for `Ata` minutes).
* **Degree Level Isolation:** Prevents mixing Undergraduate and Graduate course documents within the same process.
* **Substantial Document Isolation:** Automatically isolates valid `Edital` or `Portaria` documents containing 100 or more pages into single-document dispatches.
* **Specialized Document Handling:**
  * **Atas:** Processes composed exclusively of `Ata` documents are blocked from dispatch.
  * **Diplomas:** Restricts packaging so `Diploma` documents only pair with other Diplomas, Certificates, or Atas.
  * **Atestados:** Enforces strict category matching among all `Atestado` items in a single process.
  * **Circulares & Ofícios:** Validates recipient overlap, ensuring all linked notices share at least one common recipient.
---

## Implemented Optimization Strategies

* **Dynamic Queue Sorting by Volume:** Recalculates department processing order by sorting courses in descending order based on the total number of documents currently stacked in each course's stack.
* **First-Fit Allocation Strategy:** Scans table processes sequentially and allocates an incoming common document into the first valid process that satisfies all regulatory rules.
* **Immediate Substantial Isolation:** Scans for valid substantial documents and dispatches them immediately using a dedicated empty process.
* **Phase-Transition Flush:** When transitioning processing from Post-Graduate to Undergraduate queues, automatically dispatches active processes exceeding 245 pages to clear table space.
* **Exact Limit Dispatch:** Instantly dispatches a process as soon as its cumulative page count reaches the exact maximum capacity of 250 pages.
---

## How to Run

1. Open the project folder in your IDE.
2. Navigate to `src/SimuladorGUI.java`.
3. Execute the `main` method to start the visual simulator and track real-time metrics, including the stress level indicator, created vs. dispatched document counts, and the overall efficiency rating (automatically provided after the 120-second simulation).
   
